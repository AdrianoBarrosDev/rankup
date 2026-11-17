package com.rankup.dao;

import com.rankup.dto.ConclusaoTarefaDto;
import com.rankup.dto.TarefaDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.sql.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDao extends Dao {

    public TarefaDto criarTarefa(TarefaDto tarefa) throws SQLException {

        Connection connection = null;
        try {
            connection = this.conectar();
            String sql = "insert into tarefa(cd_grupo, descricao, dt_criacao, dt_limite, possuiRepeticao) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            ps.setInt(i++, tarefa.getCodigoGrupo());
            ps.setString(i++, tarefa.getDescricao());
            ps.setDate(i++, Date.valueOf(tarefa.getDataCriacao()));
            ps.setDate(i++, Date.valueOf(tarefa.getDataLimite()));
            ps.setBoolean(i++, tarefa.getPossuiRepeticao());
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
                tarefa.setCodigo(rs.getInt("rowid"));
                tarefa.setCodigoGrupo(rs.getInt("cd_grupo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataCriacao(rs.getDate("dt_criacao").toLocalDate());
                tarefa.setDataCriacao(rs.getDate("dt_limite").toLocalDate());

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

    public void concluirTarefa(ConclusaoTarefaDto conclusaoTarefaDto) throws SQLException {

        Connection connection = this.conectar();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tarefa_usuario ")
                .append("SET isConcluida = ?, dt_conclusao = ? ")
                .append("WHERE cd_usuario = ? AND cd_tarefa = ? ");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.executeUpdate();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
        } finally {
            this.fecharConexao();
        }
    }

    public void atribuirTarefaAosUsuarios(List<UsuarioDto> listaUsuarios, int codigoTarefa) throws SQLException {
        Connection connection = this.conectar();
        for(UsuarioDto usuario : listaUsuarios) {
            String sql = "INSERT INTO tarefa_usuario(cd_tarefa, cd_usuario) VALUES (?, ?)";

            try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                int i = 1;
                ps.setInt(i++, codigoTarefa);
                ps.setString(i++, String.valueOf(usuario.getCodigo()));
                ps.executeUpdate();
            } catch (Exception e) {
                if (connection != null) connection.rollback();
            } finally {
                this.fecharConexao();
            }
        }

    }

}
