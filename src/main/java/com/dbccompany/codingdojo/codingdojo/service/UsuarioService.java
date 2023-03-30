package com.dbccompany.codingdojo.codingdojo.service;

import com.dbccompany.codingdojo.codingdojo.dto.TipoOperacao;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegocioException;
import com.dbccompany.codingdojo.codingdojo.model.LogEntity;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.UsuarioEntity;
import com.dbccompany.codingdojo.codingdojo.repository.LogRepository;
import com.dbccompany.codingdojo.codingdojo.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final LogRepository logRepository;

    public List<UsuarioDTO> listarUsuariosMaisVelhosQueSeisMeses() {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataSeisMesesAtras = dataAtual.minusMonths(6);

        return usuarioRepository.findAllByDataCriacaoBefore(dataSeisMesesAtras).stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class)).toList();
    }

    public List<UsuarioDTO> listarUsuarioIdadeMaiorIgualDezoito(){
        return usuarioRepository.buscarUsuariosMaioresDeIdade().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class)).toList();
    }

    public UsuarioDTO listaPorId(Integer id) throws BancoDeDadosException {
        UsuarioEntity usuario =  usuarioRepository.findById(id).orElseThrow(()->new BancoDeDadosException("Usuario não encontrado!"));
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> listaPorTipo(TipoUsuario tipo) {
        List<UsuarioEntity> usuarioEntities = this.usuarioRepository.findByTipo(tipo);
        return usuarioEntities.stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
    }

    public List<UsuarioDTO> listar() throws RegraDeNegocioException {
        return usuarioRepository.findAll().stream()
                .map(usuarioEntity ->  objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
    }


    public UsuarioDTO adicionar(UsuarioCreateDTO usuarioCreateDTO) {
        Integer idUsuario = obterIdUsuarioLogado();
        Optional<UsuarioEntity> usuarioLogado = usuarioRepository.findById(idUsuario);

        UsuarioEntity usuario = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        UsuarioEntity usuarioEntity = usuarioRepository.save(usuario);
        LogEntity logEntity = new LogEntity();
        logEntity.setIdUsuarioManipulado(usuarioEntity.getId());
        logEntity.setNomeUsuarioManipulado(usuario.getNome());
        logEntity.setNomeOperador(usuarioLogado.get().getNome());
        logEntity.setIdUsuarioOperador(usuarioLogado.get().getId());
        logEntity.setTipoOperacao(TipoOperacao.CRIAR);
        logRepository.save(logEntity);

        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }
    

    public UsuarioDTO atualizar(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        Integer idUsuario = obterIdUsuarioLogado();

        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado!"));

        usuarioEntity.setEmail(usuarioCreateDTO.getEmail());
        usuarioEntity.setNome(usuarioCreateDTO.getNome());
        usuarioEntity.setAtivo(usuarioCreateDTO.getAtivo());
        usuarioEntity.setSenha(usuarioCreateDTO.getSenha());
        usuarioEntity.setDataCriacao(usuarioCreateDTO.getDataCriacao());
        usuarioEntity.setDataNascimento(usuarioCreateDTO.getDataNascimento());
        usuarioEntity.setTipo(usuarioCreateDTO.getTipo());

        usuarioRepository.save(usuarioEntity);

        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public Integer obterIdUsuarioLogado() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(()->new RegraDeNegocioException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
    }

    public Optional<UsuarioEntity> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}
