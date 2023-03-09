package com.dbccompany.codingdojo.codingdojo.model;


public enum TipoUsuario {
    NORMAL(0),
    ADMIN(1);

    private Integer tipo;

    TipoUsuario(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }
}
