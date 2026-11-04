package com.rankup.service;

import  com.rankup.dao.UsuarioDao;
import  com.rankup.dto.CadastroUsuarioDto;
import com.rankup.dto.UsuarioDto;

import java.sql.SQLException;
import  java.util.List;

public class UsuarioService {

    private UsuarioDao usuarioDao = new UsuarioDao();

    public void criarUsuario(CadastroUsuarioDto cadastroUsuarioDto){
        try {
            usuarioDao.criarUsuario(cadastroUsuarioDto);
        } catch (SQLException e) {

        }
    }

    public List<UsuarioDto> listarUsuarios() {
        try {
            return usuarioDao.listarUsuarios();
        } catch (SQLException e) {
            return null;
        }
    }
}
