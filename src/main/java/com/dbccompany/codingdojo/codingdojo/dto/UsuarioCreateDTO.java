package com.dbccompany.codingdojo.codingdojo.dto;

import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class UsuarioCreateDTO {


    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String senhha;
    private LocalDate dataCriacao;
    private Boolean ativo;
    private TipoUsuario tipo;

}
