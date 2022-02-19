package com.senai.vila.controller.service;

import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.ResidentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private ResidentRepository residentRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Resident> resident = residentRepository.findByEmail(email);
        if (resident.isPresent()) {
            return resident.get();
        }
        throw new ResidentException("Habitante não encontrado");
    }
}
