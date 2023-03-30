package com.dbccompany.codingdojo.codingdojo.service;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegocioException;
import com.dbccompany.codingdojo.codingdojo.model.CargoEntity;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.UsuarioEntity;
import com.dbccompany.codingdojo.codingdojo.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoService cargoService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);

        ReflectionTestUtils.setField(usuarioService, "passwordEncoder", passwordEncoder);
    }

    @Test
    public void deveCriarComSucesso() throws RegraDeNegocioException, BancoDeDadosException {
        // setup
        UsuarioCreateDTO usuarioCreateDTOMock =  getUsuarioCreateDTO();
        UsuarioEntity usuario = getUsuarioEntity();

        Set<UsuarioEntity> setUsuarios = new HashSet<>();

        CargoEntity cargo = new CargoEntity();
        cargo.setNome("ROLE_CLIENTE");
        cargo.setIdCargo(1);
        cargo.setUsuarioEntities(setUsuarios);


        when(usuarioService.buscarPorEmail(any())).thenReturn(Optional.empty());

        when(cargoService.pegaCargoPeloNome(any())).thenReturn(cargo);

        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO resultado = usuarioService.adicionar(usuarioCreateDTOMock);

        assertEquals("Abacaxi", resultado.getSenha());

    }

    @Test
    public void deveListarPorId() throws BancoDeDadosException {
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        UsuarioDTO usuarioDTO = usuarioService.listaPorId(usuarioEntity.getId());

        assertNotNull(usuarioDTO);
        assertEquals(usuarioEntity.getNome(), usuarioDTO.getNome());
        assertEquals(usuarioEntity.getId(), usuarioDTO.getId());
        assertEquals(usuarioEntity.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioEntity.getDataNascimento(), usuarioDTO.getDataNascimento());
    }

    protected static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioMockadoBanco = new UsuarioEntity();
        usuarioMockadoBanco.setId(1);
        usuarioMockadoBanco.setNome("Carlos Cunha");
        usuarioMockadoBanco.setEmail("carlos.cunha@emai.com");
//        usuarioMockadoBanco.setSenha("dsfasffsda");
        usuarioMockadoBanco.setAtivo(true);
        return usuarioMockadoBanco;
    }

    private static UsuarioCreateDTO getUsuarioCreateDTO() {
        UsuarioCreateDTO minhaNovaPessoa = new UsuarioCreateDTO(
                "Fulano", "carlos.cunha@emai.com", LocalDate.parse("1990-01-01"),
                "mypassword", LocalDate.now(), true,
                TipoUsuario.NORMAL
        );
        return minhaNovaPessoa;
    }

    private static void getMockedSecurityContext() {
        Integer idUsuarioToken = 1;
        Authentication authentication = Mockito.mock(Authentication.class);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(idUsuarioToken);
    }

}

