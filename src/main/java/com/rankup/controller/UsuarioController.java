package com.rankup.controller;

import com.rankup.dto.CadastroUsuarioDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.service.UsuarioService;

import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    public String criarUsuario(CadastroUsuarioDto cadastroUsuarioDto) {
        usuarioService.criarUsuario(cadastroUsuarioDto);
        return "Usuário criado com sucesso";
    }

    public List<UsuarioDto> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
}