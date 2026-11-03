package com.rankup.controller;

import com.rankup.dto.CadastroUsuarioDto;
import com.rankup.service.UsuarioService;

public class UsuarioController {

    private UsuarioService usuarioService = new UsuarioService();

    public String criarUsuario(CadastroUsuarioDto cadastroUsuarioDto) {
        usuarioService.criarUsuario(cadastroUsuarioDto);
        return "Usuário criado com sucesso";
    }

}