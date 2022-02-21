package com.senai.vila.controller.service;

import com.senai.vila.exception.CpfException;
import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.ResidentRepository;
import com.senai.vila.utils.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;

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

    public ResidentDto getResidentById(Long id) throws ResidentException {
        Optional<Resident> residentDao = residentRepository.findById(id);
        if (residentDao.isPresent()) {
            return residentDao.get().convertToDto();
        }
        throw new ResidentException("Não existe habitante com esse id");
    }

    public ResidentDto createResident(ResidentDto residentDto) throws ResidentException, CpfException {
        String cpf = residentDto.getCpf();
        residentDto.setCpf(CpfValidator.validateCpf(cpf));

        if (residentRepository.findByCpf(residentDto.getCpf()).isPresent()) {
            throw new ResidentException("Já existe um habitante com esse cpf cadastrado!");
        }
        Resident residentDao = residentRepository.save(new Resident(residentDto));
        return residentDao.convertToDto();
    }

    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
    }

    public Optional<Resident> getResidentByCpf(String cpf) {
        return residentRepository.findByCpf(cpf);
    }

    public List<ResidentDto> listByBirthMonth(Integer month) {
        if (month == null) {
            throw new IllegalArgumentException("Mês não pode ser nulo");
        }
        if (month < 1) {
            throw new IllegalArgumentException("Mês deve ser maior que zero");
        }
        if (month > 12) {
            throw new IllegalArgumentException("Mês deve ser menor que 13");
        }
        Optional<List<Resident>> residents = residentRepository.findByBirthMonth(month);
        if (residents.isPresent()) {
            return residents.get().stream().map(resident -> {
                ResidentDto residentDto = resident.convertToDto();
                residentDto.setId(resident.getId());
                return residentDto;
            }).collect(Collectors.toList());
        }
        return null;
    }

    public List<ResidentDto> listByFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo");
        }
        Optional<List<Resident>> residents = residentRepository.findByFirstName(firstName);
        if (residents.isPresent()) {
            return residents.get().stream().map(resident -> {
                ResidentDto residentDto = resident.convertToDto();
                residentDto.setId(resident.getId());
                return residentDto;
            }).collect(Collectors.toList());
        }
        return null;
    }

    public List<ResidentDto> listByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Sobrenome não pode ser nulo");
        }
        Optional<List<Resident>> residents = residentRepository.findByLastName(lastName);
        if (residents.isPresent()) {
            return residents.get().stream().map(resident -> {
                ResidentDto residentDto = resident.convertToDto();
                residentDto.setId(resident.getId());
                return residentDto;
            }).collect(Collectors.toList());
        }
        return null;
    }

    public List<ResidentDto> listByAge(Integer age) {
        if (age == null) {
            throw new IllegalArgumentException("Idade não pode ser nula");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }
        LocalDate dateBirth = LocalDate.now().minusYears(age);
        Optional<List<Resident>> residents = residentRepository.findByAge(dateBirth);
        if (residents.isPresent()) {
            return residents.get().stream().map(resident -> {
                ResidentDto residentDto = resident.convertToDto();
                residentDto.setId(resident.getId());
                return residentDto;
            }).collect(Collectors.toList());
        }
        return null;
    }

    public Double getTotalRentResidents() {
        return residentRepository.getTotalRentResidents();
    }

    public Resident getMostValuableResident() {
        return residentRepository.getMostValuableResident();
    }
}
