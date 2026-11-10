package com.rankup.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {

    public static void inicializar() {
        criarTabelaUsuario();
        criarTebelasGrupo();
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
    public static void criarTebelasGrupo(){
        String grupo = """
            CREATE TABLE IF NOT EXISTS grupo(
                cd_grupo INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                descricao TEXT,
                cd_usuario_adm TEXT NOT NULL,
                FOREIGN KEY(cd_usuario_adm) REFERENCES usuario(cd_usuario)
              )
        """;

        String grupoUsuario = """
            CREATE TABLE IF NOT EXISTS grupo_usuario(
                cd_grupo INTEGER NOT NULL,
                cd_usuario TEXT NOT NULL,
                is_adm INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY (cd_grupo, cd_usuario),
                FOREIGN KEY (cd_grupo) REFERENCES grupo(cd_grupo),
                FOREIGN KEY (cd_usuario) REFERENCES usuario(cd_usuario)
            )
        """;

        String convite = """
        CREATE TABLE IF NOT EXISTS convite_grupo (
            cd_convite INTEGER PRIMARY KEY AUTOINCREMENT,
            cd_grupo INTEGER NOT NULL,
            cd_convidado TEXT NOT NULL,
            status INTEGER NOT NULL DEFAULT 1,
            FOREIGN KEY (cd_grupo) REFERENCES grupo(cd_grupo),
            FOREIGN KEY (cd_convidado) REFERENCES usuario(cd_usuario)
        )
    """;

        Dao dao = new Dao();
        try (Connection conexao = dao.conectar();
             Statement stmt = conexao.createStatement()) {

            stmt.execute(grupo);
            stmt.execute(grupoUsuario);
            stmt.execute(convite);

            System.out.println("Tabelas de grupo criadas!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
