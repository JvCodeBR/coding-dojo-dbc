package com.dbccompany.codingdojo.codingdojo.model;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String senha;
    private LocalDate dataCriacao;
    private Boolean ativo;
    private TipoUsuario tipo;

}
