package com.rankup.controller;

import com.rankup.dto.GrupoDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.dto.UsuarioGrupoDto;
import com.rankup.service.GrupoService;

import java.util.List;

public class GrupoController {

    private GrupoService grupoService;

    public GrupoController() {
        grupoService = new GrupoService();
    }

    public String criarGrupo(GrupoDto grupoDto){
        grupoService.criarGrupo(grupoDto);
        return "Grupo criado com sucesso!";
    }

    public List<GrupoDto> listarGrupos(){
        return grupoService.listarGrupos();
    }

    public String adicionarUsuarioAoGrupo(UsuarioGrupoDto dto) {
        grupoService.adicionarUsuarioAoGrupo(dto);
        return "Usuário adicionado com sucesso!";
    }

    public List<UsuarioDto> listarUsuariosGrupo(String codigoGrupo) {
        return grupoService.listarUsuariosGrupo(codigoGrupo);
    }
}