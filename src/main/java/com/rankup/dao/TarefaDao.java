package com.rankup.dao;

import com.rankup.dto.GrupoDto;
import com.rankup.dto.TarefaDto;
import com.rankup.sql.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TarefaDao extends Dao {

    public TarefaDto criarTarefa(TarefaDto tarefa) throws SQLException {

        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "insert into tarefa(cd_grupo, descricao, dt_criacao, dt_limite) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            ps.setInt(i++, tarefa.getCodigoGrupo());
            ps.setString(i++, tarefa.getDescricao());
            ps.setDate(i++, (Date) tarefa.getDataCriacao());
            ps.setDate(i++, (Date) tarefa.getDataLimite());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tarefa.setCodigo(rs.getInt(1));
            }
            rs.close();
            ps.close();

            System.out.println("Tarefa gravada no banco");



        } catch (Exception e) {
            if (connection != null) connection.rollback();
            System.out.println("Erro ao criar tarefa no banco.");
        } finally {
            this.fecharConexao();
        }
        return tarefa;
    }

    public List<TarefaDto> listarTarefas() throws SQLException {

        List<TarefaDto> listaTarefas = new ArrayList<>();
        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "select * from tarefa";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TarefaDto tarefa = new TarefaDto();
                tarefa.setCodigo(rs.getInt("cd_grupo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataCriacao(rs.getDate("dt_ciacao"));
                tarefa.setDataCriacao(rs.getDate("dt_limite"));

                listaTarefas.add(tarefa);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            System.out.println("Erro ao listar Tarefas do banco.");
        } finally {
            this.fecharConexao();
        }
        return listaTarefas;
    }

}
