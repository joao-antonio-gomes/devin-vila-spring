package com.senai.vila.controller.service;

import com.senai.vila.exception.HabitanteException;
import com.senai.vila.model.dto.HabitanteDto;
import com.senai.vila.model.entity.Habitante;
import com.senai.vila.model.repository.HabitanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<HabitanteDto> getAllHabitantes() {
        List<Habitante> habitantes = habitanteRepository.findAll();
        return habitantes.stream().map(Habitante::converterEmDto).collect(Collectors.toList());
    }
}
