package com.dbccompany.codingdojo.codingdojo.model;

import com.dbccompany.codingdojo.codingdojo.dto.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logs")
public class LogEntity{

    @Id
    private Integer idUsuarioOperador;

    private String nomeOperador;

    private Integer idUsuarioManipulado;

    private String nomeUsuarioManipulado;

    private TipoOperacao tipoOperacao;


}
