package com.dbccompany.codingdojo.codingdojo.controller;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegociosException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UsuarioDoc {
    @Operation(summary = "lista usuarios", description = "Listar usuarios cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping(value = "/")
    public ResponseEntity<List<UsuarioDTO>>  listar() throws RegraDeNegociosException;
    @Operation(summary = "Buscar usuário por id", description = "Busca usuário com o id informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o usuário solicitado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> listarPorId(@PathVariable("id") Integer id);

    @Operation(summary = "Criar usuario", description = "Criar o usuário e enviar para o banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o usuário criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{tipo}")
    public ResponseEntity<List<UsuarioDTO>> listarPorTipo(@PathVariable("tipo") String tipo);


    @GetMapping("/maior-de-idade")
    public ResponseEntity<List<UsuarioDTO>> listarMaiorDeIdade();


    @GetMapping("/usuarios-antigos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosAntigos();


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id);
}
