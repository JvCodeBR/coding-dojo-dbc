package com.dbccompany.codingdojo.codingdojo.service;

import com.dbccompany.codingdojo.codingdojo.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public List<UsuarioDTO> listarUsuariosAntigos() {

        List<Usuario> listaRepository = usuarioRepository.list();

        return objectMapper.convertValue(, UsuarioDTO.class);
    }

    public UsuarioDTO listaPorId(Integer id) {

    }

    public List<UsuarioDTO> listaPorTipo(String tipo) {
    }

    public void delete(Integer id) {
    }

    public List<UsuarioDTO> listar() {

    }

    public UsuarioDTO listarMaiorDeIdade() {
    }
}
