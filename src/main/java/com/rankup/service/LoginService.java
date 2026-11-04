package com.rankup.service;

import com.rankup.dao.UsuarioDao;
import com.rankup.security.AutenticacaoSenha;

import java.sql.SQLException;

public class LoginService {

    private UsuarioDao usuarioDao;

    public LoginService() {
        usuarioDao = new UsuarioDao();
    }

    public boolean logar(String username, String senha) {
        try {
            return AutenticacaoSenha.verificar(senha, usuarioDao.retornarSenhaCriptografadaPorUsername(username));
        } catch (SQLException e) {

        }
        return false;
    }

}
