package com.senai.vila.controller.service;

import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidentService {

    private ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public Resident getResidentByEmail(String email) throws ResidentException {
        Optional<Resident> habitante = residentRepository.findByEmail(email);
        if (habitante.isPresent()) {
            return habitante.get();
        }
        throw new ResidentException("Habitante não encontrado");
    }

    public List<ResidentDto> getAllResidents() {
        List<Resident> residents = residentRepository.findAll();
        return residents.stream().map(Resident::convertToDto).collect(Collectors.toList());
    }

    public Resident getResidentById(Long id) {
        return null;
    }

    public Resident createResident(Resident resident) {
        return null;
    }

    public void deleteResident(Long id) {

    }

    public Resident updateResident(Long id, Resident resident) {
        return null;
    }
}
