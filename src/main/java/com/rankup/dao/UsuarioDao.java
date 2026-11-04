package com.rankup.dao;

import  com.rankup.dto.CadastroUsuarioDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.security.AutenticacaoSenha;
import com.rankup.sql.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDao extends Dao {

    public void criarUsuario(CadastroUsuarioDto cadastroUsuarioDto) throws SQLException {

        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "insert into usuario(cd_usuario, nome, username, senha) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            UUID userId = UUID.randomUUID();

            int i = 1;
            ps.setString(i++, userId.toString());
            ps.setString(i++, cadastroUsuarioDto.getName());
            ps.setString(i++, cadastroUsuarioDto.getUsername());
            ps.setString(i++, AutenticacaoSenha.criptografar(cadastroUsuarioDto.getPassword()));
            ps.executeUpdate();
            ps.close();

            System.out.println("Usuário gravado no banco: " + cadastroUsuarioDto.getUsername());

        } catch(Exception e) {
            System.out.println("Erro ao criar usuário no banco.");
        } finally {
            this.fecharConexao();
        }

    }

    public List<UsuarioDto> listarUsuarios() throws SQLException {
        Connection connection = null;
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        try {
            connection = this.conectar();
            String sql = "select cd_usuario, nome, username from usuario;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                UsuarioDto usuarioDto = new UsuarioDto();
                usuarioDto.setCodigo(UUID.fromString(rs.getString("cd_usuario")));
                usuarioDto.setNome(rs.getString("nome"));
                usuarioDto.setUsername(rs.getString("username"));
                listaUsuarios.add(usuarioDto);
            }
            rs.close();
            ps.close();

        } catch(Exception e) {
            System.out.println("Erro ao listar usuários do banco.");
        } finally {
            this.fecharConexao();
        }
        return listaUsuarios;
    }

    public String retornarSenhaCriptografadaPorUsername(String username) throws SQLException {
        Connection connection = null;
        String senhaCriptografada = "";
        try {
            connection = this.conectar();
            String sql = "select senha from usuario where username = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            int i = 1;
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                senhaCriptografada = rs.getString("senha");
            }
            rs.close();
            ps.close();

        } catch(Exception e) {
            System.out.println("Erro ao consultar senha no banco.");
        } finally {
            this.fecharConexao();
        }
        return senhaCriptografada;
    }
}
