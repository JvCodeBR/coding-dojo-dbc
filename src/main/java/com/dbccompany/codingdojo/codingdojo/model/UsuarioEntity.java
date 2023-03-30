package com.dbccompany.codingdojo.codingdojo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USUARIO")
@Getter
@Setter
public class UsuarioEntity {

    @Column(name = "ID_USUARIO")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "DATA_CRIACAO")
    private LocalDate dataCriacao;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Column(name = "TIPO")
    private TipoUsuario tipo;

}
