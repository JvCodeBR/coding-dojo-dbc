package com.dbccompany.codingdojo.codingdojo.controller;



import com.dbccompany.codingdojo.codingdojo.dto.LoginDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegocioException;
import com.dbccompany.codingdojo.codingdojo.security.AuthenticationService;
import com.dbccompany.codingdojo.codingdojo.security.TokenService;
import com.dbccompany.codingdojo.codingdojo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;

    @PostMapping // http://localhost:8080/auth
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.authenticate(loginCreateDTO), HttpStatus.OK);
    }

    @PostMapping("/create") // http://localhost:8080/auth/create
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO loginCreateDTO) throws BancoDeDadosException {
        return new ResponseEntity<>(usuarioService.adicionar(loginCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/usuario")
    public ResponseEntity<UsuarioDTO> getLoggedUser() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(),  HttpStatus.OK);
    }



}
