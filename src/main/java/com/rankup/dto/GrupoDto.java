package com.rankup.dto;

import java.util.UUID;

public class GrupoDto {
    private int codigo;
    private String nome;
    private String descricao;
    private UUID codigoUsuarioAdm;

    public int getCodigo(){
        return codigo;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UUID getCodigoUsuarioAdm(){
        return codigoUsuarioAdm;
    }

    public void setCodigoUsuarioAdm(UUID codigoUsuarioAdm){
        this.codigoUsuarioAdm = codigoUsuarioAdm;
    }
}
