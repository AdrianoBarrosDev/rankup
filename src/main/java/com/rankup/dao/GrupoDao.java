package com.rankup.dao;

import com.rankup.dto.GrupoDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.dto.UsuarioGrupoDto;
import com.rankup.sql.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class GrupoDao extends Dao {

    public GrupoDto criarGrupo(GrupoDto grupo) throws SQLException {

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

    public List<GrupoDto> listarGrupos() throws SQLException {

        List<GrupoDto> listaGrupos = new ArrayList<>();
        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "select * from grupo";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GrupoDto grupo = new GrupoDto();
                grupo.setCodigo(rs.getInt("cd_grupo"));
                grupo.setNome(rs.getString("nome"));
                grupo.setDescricao(rs.getString("descricao"));
                grupo.setCodigoUsuarioAdm(UUID.fromString(rs.getString("cd_usuario_adm")));
                listaGrupos.add(grupo);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            System.out.println("Erro ao listar grupos do banco.");
        } finally {
            this.fecharConexao();
        }
        return listaGrupos;
    }

    public void adicionarUsuarioAoGrupo(UsuarioGrupoDto usuarioGrupoDto) throws SQLException {

        List<GrupoDto> listaGrupos = new ArrayList<>();
        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "insert into grupo_usuario(cd_grupo, cd_usuario, isAdm) VALUES (?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            ps.setInt(i++, usuarioGrupoDto.codigoGrupo());
            ps.setString(i++, usuarioGrupoDto.codigoUsuario());
            ps.setInt(i++, usuarioGrupoDto.isUsuarioAdm());
            ps.executeUpdate();

            ps.close();

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            System.out.println("Erro ao listar grupos do banco.");
        } finally {
            this.fecharConexao();
        }
    }

    public List<UsuarioDto> listarUsuariosGrupo(String codigoGrupo) throws SQLException {

        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = """
                SELECT u.*
                FROM usuario u
                INNER JOIN grupo_usuario gu ON gu.cd_usuario = u.cd_usuario
                WHERE gu.cd_grupo = ?
            """;

            PreparedStatement ps = connection.prepareStatement(sql);

            int i = 1;
            ps.setInt(i, Integer.parseInt(codigoGrupo));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UsuarioDto usuario = new UsuarioDto();
                usuario.setCodigo(UUID.fromString(rs.getString("cd_usuario")));
                usuario.setNome(rs.getString("nome"));
                usuario.setUsername(rs.getString("username"));
                listaUsuarios.add(usuario);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            System.out.println("Erro ao listar usuários do grupo do banco.");
        } finally {
            this.fecharConexao();
        }
        return listaUsuarios;
    }
}