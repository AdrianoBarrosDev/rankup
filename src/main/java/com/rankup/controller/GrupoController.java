package com.rankup.controller;

import com.rankup.dto.GrupoDto;
import com.rankup.service.GrupoService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class GrupoController {

    private GrupoService grupoService;

    public GrupoController() {grupoService = new GrupoService();}

    public GrupoDto criar(GrupoDto grupo) throws SQLException {
        return grupoService.criarGrupo(grupo);
    }
}