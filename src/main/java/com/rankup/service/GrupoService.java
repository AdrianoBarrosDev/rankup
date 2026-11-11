package com.rankup.service;

import com.rankup.dto.CadastroUsuarioDto;
import com.rankup.dao.GrupoDao;
import com.rankup.dto.GrupoDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.dto.UsuarioGrupoDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoService {

    private GrupoDao grupoDao;

    public GrupoService() {
        this.grupoDao = new GrupoDao();
    }

    public void criarGrupo(GrupoDto grupoDto){
        try {
            grupoDao.criarGrupo(grupoDto);
        } catch (SQLException e) {

        }
    }

    public List<GrupoDto> listarGrupos(){
        List<GrupoDto> listaGrupos = new ArrayList<>();
        try {
            listaGrupos = grupoDao.listarGrupos();
        } catch (SQLException e) {

        }
        return listaGrupos;
    }

    public void adicionarUsuarioAoGrupo(UsuarioGrupoDto dto) {
        try {
            grupoDao.adicionarUsuarioAoGrupo(dto);
        } catch (SQLException e) {

        }
    }

    public List<UsuarioDto> listarUsuariosGrupo(String codigoGrupo) {
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = grupoDao.listarUsuariosGrupo(codigoGrupo);
        } catch (SQLException e) {

        }
        return listaUsuarios;
    }
}
