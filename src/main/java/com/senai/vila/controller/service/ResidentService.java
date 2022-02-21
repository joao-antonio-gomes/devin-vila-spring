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

    public ResidentDto createResident(ResidentDto residentDto) throws ResidentException, CpfException {
        if (residentDto == null) {
            throw new ResidentException("Habitante não pode ser nulo");
        }
        String cpf = residentDto.getCpf();
        residentDto.setCpf(CpfValidator.validateCpf(cpf));

        if (residentRepository.findByCpf(residentDto.getCpf()).isPresent()) {
            throw new ResidentException("Já existe um habitante com esse cpf cadastrado!");
        }
        Resident residentDao = residentRepository.save(new Resident(residentDto));
        return residentDao.convertToDto();
    }

    public Resident getResidentByEmail(String email) throws ResidentException {
        if (email == null) {
            throw new ResidentException("Email não pode ser nulo");
        }
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

    public void deleteResident(Long id) throws ResidentException {
        if (id == null) {
            throw new ResidentException("Id não pode ser nulo");
        }
        residentRepository.deleteById(id);
    }

    public ResidentDto getResidentByCpf(String cpf) throws ResidentException, CpfException {
        if (cpf == null) {
            throw new ResidentException("Cpf não pode ser nulo");
        }
        cpf = CpfValidator.validateCpf(cpf);
        Optional<Resident> residentOptional = residentRepository.findByCpf(cpf);
        if (residentOptional.isPresent()) {
            return residentOptional.get().convertToDto();
        }
        throw new ResidentException("Habitante não encontrado");
    }

    public List<ResidentDto> listByBirthMonth(Integer month) throws ResidentException {
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
        throw new ResidentException("Não existe habitantes com esse mês de nascimento");
    }

    public List<ResidentDto> listByFirstName(String firstName) throws ResidentException {
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
        throw new ResidentException("Não existe habitantes com esse primeiro nome");
    }

    public List<ResidentDto> listByLastName(String lastName) throws ResidentException {
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
        throw new ResidentException("Não existe habitantes com esse sobrenome");
    }

    public List<ResidentDto> listByAge(Integer age) throws ResidentException {
        if (age == null) {
            throw new IllegalArgumentException("Idade não pode ser nula");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }
        LocalDate dateBirth = LocalDate.now().minusYears(age);
        Optional<List<Resident>> residents = residentRepository.findByAge(age);
        if (residents.isPresent()) {
            return residents.get().stream().map(resident -> {
                ResidentDto residentDto = resident.convertToDto();
                residentDto.setId(resident.getId());
                return residentDto;
            }).collect(Collectors.toList());
        }
        throw new ResidentException("Não existe habitantes com essa idade");
    }

    public Double getTotalRentResidents() {
        return residentRepository.getTotalRentResidents();
    }

    public Resident getMostValuableResident() {
        return residentRepository.getMostValuableResident();
    }
}
