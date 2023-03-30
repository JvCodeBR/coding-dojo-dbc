package com.dbccompany.codingdojo.codingdojo.controller;

import com.dbccompany.codingdojo.codingdojo.security.AuthenticationService;
import com.dbccompany.codingdojo.codingdojo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String auth(@RequestBody @Valid LoginCreateDTO loginCreateDTO) throws RegraDeNegocioException {
        return tokenService.getToken(authenticationService.authUser(loginCreateDTO));
    }

    @PostMapping("/create") // http://localhost:8080/auth/create
    public ResponseEntity<LoginDTO> create(@RequestBody @Valid LoginCreateDTO loginCreateDTO) {
        return new ResponseEntity<>(usuarioService.create(loginCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/usuario")
    public ResponseEntity<LoginDTO> getLoggedUser() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(),  HttpStatus.OK);
    }

}
