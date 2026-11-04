package com.rankup.controller;

import com.rankup.service.LoginService;

public class LoginController {

    private LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    public String logar(String username, String senha) {
        if(loginService.logar(username, senha)) {
            return "Usuário logado";
        } else {
            return "Autenticação inválida! Usuário ou senha incorretos.";
        }
    }

}
