package com.senai.vila.config.security;

import com.senai.vila.controller.service.TokenService;
import com.senai.vila.model.entity.Habitante;
import com.senai.vila.model.repository.HabitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAutenticacaoFiltro extends OncePerRequestFilter {

    private TokenService tokenService;
    private HabitanteRepository habitanteRepository;

    public JwtAutenticacaoFiltro(TokenService tokenService, HabitanteRepository habitanteRepository) {
        this.tokenService = tokenService;
        this.habitanteRepository = habitanteRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        boolean tokenValido = tokenService.isTokenValido(token);
        if (tokenValido) {
            autenticarUsuario(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Habitante habitante = habitanteRepository.findById(idUsuario).get();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(habitante, null, habitante.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
