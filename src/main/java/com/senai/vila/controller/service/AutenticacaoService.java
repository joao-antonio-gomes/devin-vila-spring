package com.senai.vila.controller.service;

import com.senai.vila.exception.HabitanteException;
import com.senai.vila.model.entity.Habitante;
import com.senai.vila.model.repository.HabitanteRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private HabitanteRepository habitanteRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Habitante> habitante = habitanteRepository.findByEmail(email);
        if (habitante.isPresent()) {
            return habitante.get();
        }
        throw new HabitanteException("Habitante não encontrado");
    }
}
