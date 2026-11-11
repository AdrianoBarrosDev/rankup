package com.rankup.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PoolConexoes {

    private static final String URL = "jdbc:sqlite:rankup.db";
    private static final int MAX_CONNECTIONS = 10;
    private static final long TIMEOUT_MS = 5000;

    private static final PoolDto[] conexoes = new PoolDto[MAX_CONNECTIONS];
    private static final boolean[] emUso = new boolean[MAX_CONNECTIONS];

    private PoolConexoes() {}

    public static synchronized PoolDto getConnection() throws SQLException {
        long limite = System.currentTimeMillis() + TIMEOUT_MS;

        while (true) {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                if (emUso[i]) continue;

                if (conexoes[i] == null || conexoes[i].connection().isClosed()) {
                    conexoes[i] = new PoolDto(i, abrirConexao());
                }
                emUso[i] = true;
                return conexoes[i];
            }

            // pool lotado: espera alguém devolver uma conexão
            long restante = limite - System.currentTimeMillis();
            if (restante <= 0) {
                throw new SQLException("Pool de conexões esgotado");
            }
            try {
                PoolConexoes.class.wait(restante);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("Espera por conexão interrompida", e);
            }
        }
    }

    public static synchronized void releaseConnection(PoolDto dto) {
        try {
            Connection conn = dto.connection();
            // não devolve ao pool uma conexão com transação pendente
            if (!conn.isClosed() && !conn.getAutoCommit()) {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            // conexão em estado ruim: descarta, será recriada no próximo uso
            try { dto.connection().close(); } catch (SQLException ignored) { }
        }
        emUso[dto.codigo()] = false;
        PoolConexoes.class.notifyAll();
    }

    /** Chame ao encerrar o servidor. */
    public static synchronized void closeAll() {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            if (conexoes[i] != null) {
                try { conexoes[i].connection().close(); } catch (SQLException ignored) { }
                conexoes[i] = null;
            }
            emUso[i] = false;
        }
    }

    private static Connection abrirConexao() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");   // SQLite deixa desligado por padrão
            st.execute("PRAGMA busy_timeout = 5000"); // espera em vez de "database is locked"
            st.execute("PRAGMA journal_mode = WAL");  // leitura não bloqueia escrita
        }
        return conn;
    }
}