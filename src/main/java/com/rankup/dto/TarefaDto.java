package com.rankup.dto;

import java.util.Date;

public class TarefaDto {
    private int codigo;
    private int codigoGrupo;
    private String descricao;
    private Date dataCriacao;
    private Date dataLimite;
    private boolean possuiRepeticao;

    public int getCodigo(){
        return codigo;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public int getCodigoGrupo(){
        return codigoGrupo;
    }

    public void setCodigoGrupo(int codigoGrupo){
        this.codigoGrupo = codigoGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    public boolean getPossuiRepeticao(){
        return possuiRepeticao;
    }

    public void setPossuiRepeticao(boolean possuiRepeticao){
        this.possuiRepeticao = possuiRepeticao;
    }
}
