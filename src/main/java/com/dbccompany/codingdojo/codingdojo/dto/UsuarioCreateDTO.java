package com.dbccompany.codingdojo.codingdojo.dto;

import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioCreateDTO {


    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String senha;
    private LocalDate dataCriacao;
    private Boolean ativo;
    private TipoUsuario tipo;

}
