package com.rankup.enums;

public enum StatusEnum {
    Pendente(1, "Pendente"),
    Aceito(2, "Aceito"),
    Recusado(3, "Recusado");

    private final int codigo;
    private final String descricao;

    StatusEnum(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return  descricao;
    }

    public static StatusEnum fromCodigo(int codigo){
        for(StatusEnum s : values()){
            if(s.codigo == codigo) return s;
        }
        throw new IllegalArgumentException("Status inválido: " + codigo);
    }
}
