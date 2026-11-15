package com.rankup.controller;

import com.rankup.dto.GrupoDto;
import com.rankup.dto.TarefaDto;
import com.rankup.dto.UsuarioGrupoDto;
import com.rankup.service.GrupoService;
import com.rankup.service.TarefaService;

import java.util.List;

public class TarefaController {

    private TarefaService tarefaService;

    public TarefaController() {
        tarefaService = new TarefaService();
    }

    public String criarTarefa(TarefaDto tarefaDto){
        tarefaService.criarTarefa(tarefaDto);
        return "tarefa criada com sucesso!";
    }

    public List<TarefaDto> listarTarefas(){
        return tarefaService.listarTarefas();
    }
}
