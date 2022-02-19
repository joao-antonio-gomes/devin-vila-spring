package com.senai.vila.config.security;

import com.senai.vila.controller.service.TokenService;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.ResidentRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private ResidentRepository residentRepository;

    public JwtAuthenticationFilter(TokenService tokenService, ResidentRepository residentRepository) {
        this.tokenService = tokenService;
        this.residentRepository = residentRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        boolean tokenValido = tokenService.isTokenValid(token);
        if (tokenValido) {
            autenticarUsuario(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) {
        Long idUser = tokenService.getIdUser(token);
        Resident resident = residentRepository.findById(idUser).get();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(resident, null, resident.getAuthorities());
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
