package com.dbccompany.codingdojo.codingdojo.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    // TO-DO: refatorar de acordo com o que foi decidido durante a daily de quarta

    private final TokenService tokenService;

    public TokenAuthenticationFilter(@Lazy TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        UsernamePasswordAuthenticationToken user = tokenService.validarToken(token);

        SecurityContextHolder.getContext().setAuthentication(user);

        filterChain.doFilter(request, response);
    }
}
