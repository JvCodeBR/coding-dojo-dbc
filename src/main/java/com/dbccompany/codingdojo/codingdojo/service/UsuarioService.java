package com.dbccompany.codingdojo.codingdojo.service;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegociosException;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.Usuario;
import com.dbccompany.codingdojo.codingdojo.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public List<UsuarioDTO> listarUsuariosAntigos() {
//        return this.usuarioRepository.listarUsuariosAntigos();
        return null;
    }

    public List<UsuarioDTO> listaPorId(Integer id) {
//        return this.usuarioRepository.listarPorId(id);
        return null;
    }

    public List<UsuarioDTO> listaPorTipo(TipoUsuario tipo) {
        return this.usuarioRepository.listaPorTipo(tipo);
    }

    public void delete(Integer id) {
        this.usuarioRepository.delete(id);
    }

    public List<UsuarioDTO> listar() {
//        return usuarioRepository.list().stream()
//                .map(usuario-> objectMapper.convertValue(usuario, UsuarioDTO.class))
//                .collect(Collectors.toList());
        return null;
    }

    public UsuarioDTO listarMaiorDeIdade() {
        return this.usuarioRepository.listarMaiorDeIdade();
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegociosException {
        try {
            return objectMapper.convertValue(
                    this.usuarioRepository.adicionar(objectMapper.convertValue(usuarioCreateDTO, Usuario.class)),
                    UsuarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegociosException("Erro no banco!");
        }
    }

    public UsuarioDTO atualizar(Integer id, UsuarioCreateDTO usuarioCreateDTO) {
        return this.usuarioRepository.atualizar(id, usuarioCreateDTO);
    }
}
