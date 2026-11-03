package com.rankup.service;

import  com.rankup.dao.UsuarioDao;
import  com.rankup.dto.CadastroUsuarioDto;
import  java.util.List;

public class UsuarioService {

    private UsuarioDao usuarioDao = new UsuarioDao();

    public void criarUsuario(CadastroUsuarioDto cadastroUsuarioDto){
        usuarioDao.criarUsuario(cadastroUsuarioDto);
    }
}
