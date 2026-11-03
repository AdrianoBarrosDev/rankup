package com.rankup.dao;

import  com.rankup.dto.CadastroUsuarioDto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private final List<CadastroUsuarioDto> banco = new ArrayList<>();

    public void criarUsuario(CadastroUsuarioDto cadastroUsuarioDto){
        banco.add(cadastroUsuarioDto);
        System.out.println("Usuário gravado no banco: " + cadastroUsuarioDto.getUsername());
    }


}
