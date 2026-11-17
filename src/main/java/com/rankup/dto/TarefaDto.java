package com.rankup.dto;


import java.time.LocalDate;

public class TarefaDto {
    private int codigo;
    private int codigoGrupo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataLimite;
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

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = LocalDate.parse(dataCriacao);
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(String dataLimite) {
        this.dataLimite = LocalDate.parse(dataLimite);
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public boolean getPossuiRepeticao(){
        return possuiRepeticao;
    }

    public void setPossuiRepeticao(boolean possuiRepeticao){
        this.possuiRepeticao = possuiRepeticao;
    }
}
