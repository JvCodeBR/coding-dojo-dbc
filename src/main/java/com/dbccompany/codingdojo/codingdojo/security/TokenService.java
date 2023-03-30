package com.dbccompany.codingdojo.codingdojo.security;

import com.dbccompany.codingdojo.codingdojo.model.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    // TO-DO: refatorar de acordo com o que foi decidido durante a daily de quarta
    private static final String CARGOS = "cargos";
    private static final String BEARER = "Bearer ";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        UsuarioEntity usuarioEntity = (UsuarioEntity) authentication.getPrincipal();
        Date hoje = new Date();
        Date expiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        String token = Jwts.builder()
                .claim("login", usuarioEntity.getLogin())
                .claim(Claims.ID, usuarioEntity.getIdUsuario())
                .claim(CARGOS, usuarioEntity.getCargos().stream().map(CargoEntity::getAuthority).toList())
                .setIssuedAt(hoje)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return BEARER + token;
    }

    public UsernamePasswordAuthenticationToken validarToken(String token) {
        if(token == null){
            return null;
        }

        token = token.replace(BEARER, "").trim();

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        Integer id = claims.get(Claims.ID, Integer.class);
        List<String> cargos = claims.get(CARGOS, List.class);
        List<SimpleGrantedAuthority> cargosUsuario = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(id, null, cargosUsuario);
    }
}

