package com.dbccompany.codingdojo.codingdojo.controller;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegocioException;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>>  listar() throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> listarPorId(@PathVariable("id") Integer id) throws BancoDeDadosException {
        return ResponseEntity.ok(usuarioService.listaPorId(id));
    }

    @GetMapping("/{tipo}/tipo")
    public ResponseEntity<List<UsuarioDTO>> listarPorTipo(@PathVariable("tipo") TipoUsuario tipo) {
        return ResponseEntity.ok(usuarioService.listaPorTipo(tipo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> adicionar(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        return ResponseEntity.ok(usuarioService.adicionar(usuarioCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.atualizar(usuarioCreateDTO));
    }

    @GetMapping("/maior-de-idade")
    public ResponseEntity<List<UsuarioDTO>> listarMaiorDeIdade() {
        return ResponseEntity.ok(usuarioService.listarUsuarioIdadeMaiorIgualDezoito());
    }

    @GetMapping("/usuarios-antigos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosAntigos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosMaisVelhosQueSeisMeses());
    }

}
