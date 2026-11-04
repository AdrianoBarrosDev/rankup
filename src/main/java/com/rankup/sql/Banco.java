package com.rankup.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {

    public static void inicializar() {
        criarTabelaUsuario();
    }
    public static void criarTabelaUsuario() {

        String sql = """
            CREATE TABLE IF NOT EXISTS usuario (
                rowid INTEGER PRIMARY KEY AUTOINCREMENT,
                cd_usuario TEXT NOT NULL UNIQUE,
                nome TEXT NOT NULL,
                username TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL
            )
        """;

        Dao dao = new Dao();
        try (Connection conexao = dao.conectar();
             Statement stmt = conexao.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela criada!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
