package com.senai.vila.controller.service;

import com.senai.vila.exception.HabitanteException;
import com.senai.vila.model.entity.Habitante;
import com.senai.vila.model.repository.HabitanteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HabitanteService {

    private HabitanteRepository habitanteRepository;

    public HabitanteService(HabitanteRepository habitanteRepository) {
        this.habitanteRepository = habitanteRepository;
    }

    public Habitante loadHabitanteByEmail(String email) throws HabitanteException {
        Optional<Habitante> habitante = habitanteRepository.findByEmail(email);
        if (habitante.isPresent()) {
            return habitante.get();
        }
        throw new HabitanteException("Habitante não encontrado");
    }
}
