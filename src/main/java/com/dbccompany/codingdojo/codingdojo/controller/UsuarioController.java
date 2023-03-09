package com.dbccompany.codingdojo.codingdojo.controller;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>>  listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(usuarioService.listaPorId(id));
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<UsuarioDTO>> listarPorTipo(@PathVariable("tipo") String tipo) {
        return ResponseEntity.ok(usuarioService.listaPorTipo(tipo));
    }

    @GetMapping("/maior-de-idade")
    public ResponseEntity<List<UsuarioDTO>> listarMaiorDeIdade() {
        return ResponseEntity.ok(usuarioService.listarMaiorDeIdade());
    }

    @GetMapping("/usuarios-antigos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosAntigos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosAntigos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(usuarioService.delete(id));
    }

}
