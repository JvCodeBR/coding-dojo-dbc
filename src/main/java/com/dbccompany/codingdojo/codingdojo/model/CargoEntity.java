package com.dbccompany.codingdojo.codingdojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CARGO")
public class CargoEntity  implements GrantedAuthority {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cargo")
    @SequenceGenerator(name = "seq_cargo", sequenceName = "seq_cargo", allocationSize = 1)
    @Id
    @Column(name = "ID_CARGO")
    private int idCargo;

    @Column(name = "NOME")
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CARGO_X_USUARIO",
            joinColumns = @JoinColumn(name = "id_cargo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    @JsonIgnore
    private Set<UsuarioEntity> usuarioEntities;

    @Override
    public String getAuthority() {
        return nome;
        //ROLE_ADMIN
        //ROLE_COMPANHIA
        //ROLE_COMPRADOR
    }
}