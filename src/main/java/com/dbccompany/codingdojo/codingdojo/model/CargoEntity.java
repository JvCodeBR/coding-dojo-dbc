package com.dbccompany.codingdojo.codingdojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "CARGO")
public class CargoEntity implements {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQ")
    @SequenceGenerator(name = "CARGO_SEQ", sequenceName = "seq_cargo", allocationSize = 1)
    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "nome")
    private String nome;

    // RELACIONAMENTO COM USUARIO
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cargos")
    @JsonIgnore
    private Set<UsuarioEntity> usuarios;

    @Override
    public String getAuthority() {
        return nome;
    }
}
