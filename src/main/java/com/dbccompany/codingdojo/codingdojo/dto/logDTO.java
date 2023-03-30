package com.dbccompany.codingdojo.codingdojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class logDTO {
    Integer idUsuarioAlterado;
    String nomeUsuarioAlerado;
    Integer IdUsuarioRequisicao;
    String nomeUsuarioRequisicao;
    TipoOperacao tipoOperacao;
}
