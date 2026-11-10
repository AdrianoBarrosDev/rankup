package com.rankup.dao;

import com.rankup.dto.GrupoDto;
import com.rankup.sql.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GrupoDao extends Dao {

    public GrupoDto inserir(GrupoDto grupo) throws SQLException {

        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "insert into grupo(nome, descricao, cd_usuario_adm) VALUES (?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            ps.setString(i++, grupo.getNome());
            ps.setString(i++, grupo.getDescricao());
            ps.setString(i++, grupo.getCodigoUsuarioAdm().toString());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                grupo.setCodigo(rs.getInt(1));
            }
            rs.close();
            ps.close();

            String sqlMembro = "insert into grupo_usuario(cd_grupo, cd_usuario, is_adm) VALUES (?, ?, 1)";

            PreparedStatement psMembro = connection.prepareStatement(sqlMembro);
            psMembro.setInt(1, grupo.getCodigo());
            psMembro.setString(2, grupo.getCodigoUsuarioAdm().toString());
            psMembro.executeUpdate();
            psMembro.close();

            System.out.println("Grupo gravado no banco: " + grupo.getNome());

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            System.out.println("Erro ao criar grupo no banco.");
        } finally {
            this.fecharConexao();
        }
        return grupo;
    }
}