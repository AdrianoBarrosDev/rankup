package com.rankup.service;

import com.rankup.dto.ConclusaoTarefaDto;
import com.rankup.dto.TarefaDto;
import com.rankup.dao.TarefaDao;
import com.rankup.dto.UsuarioDto;
import com.rankup.exception.TarefaException;
import com.rankup.util.UtilString;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaService {

    private static final String CONCLUSAO_TAREFA_INVALIDA = "Conclusão da tarefa inválida!";
    private static final String ERRO_CONCLUSAO_TAREFA_INVALIDA = "Erro ao concluir tarefa do banco.";
    private static final String ERRO_ATRIBUICAO_TAREFAS_USUARIOS = "Erro ao atribuir tarefas aos usuários no banco.";

    private TarefaDao tarefaDao;
    private GrupoService grupoService;

    public TarefaService() {
        this.tarefaDao = new TarefaDao();
        this.grupoService = new GrupoService();
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

    public void concluirTarefa(ConclusaoTarefaDto conclusaoTarefaDto) {
        if(UtilString.isVazioOrNull(conclusaoTarefaDto.getCodigoUsuario()) || UtilString.isVazioOrNull(conclusaoTarefaDto.getCodigoTarefa())) {
            throw new TarefaException(CONCLUSAO_TAREFA_INVALIDA);
        }

        try {
            tarefaDao.concluirTarefa(conclusaoTarefaDto);
        } catch(SQLException e) {
            System.out.println(ERRO_CONCLUSAO_TAREFA_INVALIDA);
        }

    }

    public void atribuirTarefaAosUsuariosDoGrupo(int codigoGrupo, int codigoTarefa) {
        List<UsuarioDto> listaUsuarios = grupoService.listarUsuariosGrupo(String.valueOf(codigoGrupo));
        if(listaUsuarios == null || listaUsuarios.isEmpty()) {
            return;
        }
        try {
            tarefaDao.atribuirTarefaAosUsuarios(listaUsuarios, codigoTarefa);
        } catch (SQLException e) {
            System.out.println(ERRO_ATRIBUICAO_TAREFAS_USUARIOS);
        }
    }
}
