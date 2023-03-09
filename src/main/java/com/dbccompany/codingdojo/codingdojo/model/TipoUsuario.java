package com.dbccompany.codingdojo.codingdojo.model;

public enum TipoUsuario {
    NORMAL(0),
    ADMIN(1);

    private Integer tipo;

    private TipoUsuario(Integer tipo) {
        this.tipo = tipo;
    }
}
