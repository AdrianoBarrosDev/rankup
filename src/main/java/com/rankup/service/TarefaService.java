package com.rankup.service;

import com.rankup.dto.TarefaDto;
import com.rankup.dao.TarefaDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaService {

    private TarefaDao tarefaDao;

    public TarefaService() {
        this.tarefaDao = new TarefaDao();
    }

    public void criarTarefa(TarefaDto tarefaDto){
        try {
            tarefaDao.criarTarefa(tarefaDto);
        } catch (SQLException e) {

        }
    }

    public List<TarefaDto> listarTarefas(){
        List<TarefaDto> listaTarefas = new ArrayList<>();
        try {
            listaTarefas = tarefaDao.listarTarefas();
        } catch (SQLException e) {

        }
        return listaTarefas;
    }
}
