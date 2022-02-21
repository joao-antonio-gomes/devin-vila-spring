package com.senai.vila.controller.service;

import com.senai.vila.exception.CpfException;
import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.ResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResidentServiceTest {

    private ResidentService residentService;
    private ResidentRepository residentRepository;

    @BeforeEach
    public void setUp() {
        this.residentRepository = Mockito.mock(ResidentRepository.class);
        this.residentService = new ResidentService(residentRepository);
    }

    @Test
    public void shouldThrowErrorWhenResidentDtoIsNull() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.createResident(null));
        assertEquals("Habitante não pode ser nulo", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenCpfIsInvalid() {
        ResidentDto residentDto = new ResidentDto();
        residentDto.setCpf("12345678901");
        CpfException cpfException = assertThrows(CpfException.class, () -> residentService.createResident(residentDto));
        assertEquals("CPF inválido", cpfException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenAlreadyHaveAnotherResidentWithTheSameCpf() throws CpfException {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident();
        resident.setCpf("09355872925");
        when(residentRepository.findByCpf(resident.getCpf())).thenReturn(Optional.of(resident));
        ResidentException exception = assertThrows(ResidentException.class, () -> residentService.createResident(residentDto));
        assertEquals("Já existe um habitante com esse cpf cadastrado!", exception.getMessage());
    }

    @Test
    public void shouldFormatCpfWhenIsValid() throws CpfException, ResidentException {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        when(residentRepository.save(Mockito.any(Resident.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResidentDto resident = residentService.createResident(residentDto);
        assertEquals("093.558.729-25", resident.getCpf());
    }

    @Test
    public void shouldFindById() throws ResidentException {
        Resident resident = new Resident();
        resident.setId(1L);
        resident.setFirsName("Joao");
        when(residentRepository.findById(1L)).thenReturn(Optional.of(resident));
        ResidentDto residentFound = residentService.getResidentById(1L);
        assertEquals("Joao", residentFound.getFirstName());
        verify(residentRepository, atLeastOnce()).findById(1L);
        assertNotNull(residentFound);
    }

    @Test
    public void shouldThrowErrorWhenDidntFindResidentById() {
        ResidentException exception = assertThrows(ResidentException.class, () -> residentService.getResidentById(1L));
        assertEquals("Não existe habitante com esse id", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenSearchByEmailAndEmailIsNull() {
        ResidentException exception = assertThrows(ResidentException.class, () -> residentService.getResidentByEmail(null));
        assertEquals("Email não pode ser nulo", exception.getMessage());
    }

    @Test
    public void shouldReturnResidentWhenSearchByEmail() throws ResidentException {
        Resident resident = new Resident();
        resident.setEmail("joao@email.com");
        when(residentRepository.findByEmail(resident.getEmail())).thenReturn(Optional.of(resident));
        Resident residentFound = residentService.getResidentByEmail(resident.getEmail());
        assertEquals("joao@email.com", residentFound.getEmail());
        verify(residentRepository, atLeastOnce()).findByEmail(resident.getEmail());
        assertNotNull(residentFound);
    }

    @Test
    public void shouldThrowErrorWhenResidentNotFoundByEmail() throws ResidentException {
        Resident resident = new Resident();
        resident.setEmail("joao@email.com");
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.getResidentByEmail(resident.getEmail()));
        verify(residentRepository, atLeastOnce()).findByEmail(resident.getEmail());
        assertEquals("Habitante não encontrado", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenTryDeleteAndIdIsNull() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.deleteResident(null));
        assertEquals("Id não pode ser nulo", residentException.getMessage());
    }

    @Test
    public void shouldDeleteResident() throws ResidentException {
        Resident resident = new Resident();
        resident.setId(1L);
        when(residentRepository.findById(1L)).thenReturn(Optional.of(resident));
        residentService.deleteResident(1L);
        verify(residentRepository, atLeastOnce()).deleteById(resident.getId());
    }

    @Test
    public void shouldThrowErrorWhenTryFindResidentByCpfAndCpfIsNull() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.getResidentByCpf(null));
        assertEquals("Cpf não pode ser nulo", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenDidntFindResidentByCpf() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.getResidentByCpf("09355872925"));
        assertEquals("Habitante não encontrado", residentException.getMessage());
    }

    @Test
    public void shouldReturnResidentWhenSearchByCpf() throws ResidentException, CpfException {
        Resident resident = new Resident();
        resident.setCpf("09355872925");
        when(residentRepository.findByCpf(resident.getCpf())).thenReturn(Optional.of(resident));
        ResidentDto residentFound = residentService.getResidentByCpf("09355872925");
        verify(residentRepository, atLeastOnce()).findByCpf(resident.getCpf());
        assertNotNull(residentFound);
    }

    @Test
    public void shouldThrowErrorWhenListResidentsByBirthMonthNull() {
        IllegalArgumentException residentException =
                assertThrows(IllegalArgumentException.class, () -> residentService.listByBirthMonth(null));
        assertEquals("Mês não pode ser nulo", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenListResidentsByBirthMonthMinorThanOne() {
        IllegalArgumentException residentException =
                assertThrows(IllegalArgumentException.class, () -> residentService.listByBirthMonth(0));
        assertEquals("Mês deve ser maior que zero", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenListResidentsByBirthMonthBiggerThanTwelve() {
        IllegalArgumentException residentException =
                assertThrows(IllegalArgumentException.class, () -> residentService.listByBirthMonth(13));
        assertEquals("Mês deve ser menor que 13", residentException.getMessage());
    }

    @Test
    public void shouldReturnListOfResidentWhenSearchByBirthMonth() throws ResidentException {
        List<Resident> residents = new ArrayList<>();
        Resident resident = new Resident();
        resident.setBirthDate(LocalDate.of(2000, 1, 1));
        residents.add(resident);
        when(residentRepository.findByBirthMonth(1)).thenReturn(Optional.of(residents));
        List<ResidentDto> residentsFound = residentService.listByBirthMonth(1);
        verify(residentRepository, atLeastOnce()).findByBirthMonth(1);
        assertEquals(1, residentsFound.size());
        assertNotNull(residentsFound);
    }

    @Test
    public void shouldThrowErrorWhenSearchByBirthMonthAndDontFoundResident() throws ResidentException {
        ResidentException residentException =
                assertThrows(ResidentException.class, () -> residentService.listByBirthMonth(1));
        assertEquals("Não existe habitantes com esse mês de nascimento", residentException.getMessage());
    }

    @Test
    public void shouldReturnListOfResidentDtoWhenGetAll() {
        List<Resident> residents = new ArrayList<>();
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        residents.add(resident);
        when(residentRepository.findAll()).thenReturn(residents);
        List<ResidentDto> residentsFound = residentService.getAllResidents();
        verify(residentRepository, atLeastOnce()).findAll();
        assertEquals(1, residentsFound.size());
        assertNotNull(residentsFound);
    }

    @Test
    public void shouldThrowErrorWhenSearchResidentByNullFirstName() {
        IllegalArgumentException residentException = assertThrows(IllegalArgumentException.class, () -> residentService.listByFirstName(null));
        assertEquals("Nome não pode ser nulo", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenDidntFindResidentByFirstName() throws ResidentException {
        List<Resident> residents = new ArrayList<>();
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        residents.add(resident);
        when(residentRepository.findByFirstName("Joao")).thenReturn(Optional.of(residents));
        List<ResidentDto> joao = residentService.listByFirstName("Joao");
        verify(residentRepository, atLeastOnce()).findByFirstName("Joao");
        assertEquals(1, joao.size());
        assertNotNull(joao);
    }

    @Test
    public void shouldReturnListOfResidentWithFirstName() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.listByFirstName("Joao"));
        assertEquals("Não existe habitantes com esse primeiro nome", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenSearchResidentByNullLastName() {
        IllegalArgumentException residentException = assertThrows(IllegalArgumentException.class, () -> residentService.listByLastName(null));
        assertEquals("Sobrenome não pode ser nulo", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenDidntFindResidentByLastName() throws ResidentException {
        List<Resident> residents = new ArrayList<>();
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        residents.add(resident);
        when(residentRepository.findByLastName("Gomes")).thenReturn(Optional.of(residents));
        List<ResidentDto> joao = residentService.listByLastName("Gomes");
        verify(residentRepository, atLeastOnce()).findByLastName("Gomes");
        assertEquals(1, joao.size());
        assertNotNull(joao);
    }

    @Test
    public void shouldReturnListOfResidentWithLastName() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.listByLastName("Joao"));
        assertEquals("Não existe habitantes com esse sobrenome", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenSearchResidentByNullAge() {
        IllegalArgumentException residentException = assertThrows(IllegalArgumentException.class, () -> residentService.listByAge(null));
        assertEquals("Idade não pode ser nula", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenSearchResidentByInvalidAge() {
        IllegalArgumentException residentException = assertThrows(IllegalArgumentException.class, () -> residentService.listByAge(-1));
        assertEquals("Idade não pode ser negativa", residentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenDidntFindResidentByAge() {
        ResidentException residentException = assertThrows(ResidentException.class, () -> residentService.listByAge(21));
        assertEquals("Não existe habitantes com essa idade", residentException.getMessage());
    }

    @Test
    public void shouldReturnListOfResidentWithAge() throws ResidentException {
        LocalDate birthDate = LocalDate.of(1996, 12, 04);
        List<Resident> residents = new ArrayList<>();
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925",
                birthDate, 2000.0,"joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        residents.add(resident);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        when(residentRepository.findByAge(age)).thenReturn(Optional.of(residents));
        List<ResidentDto> joao = residentService.listByAge(age);
        verify(residentRepository, atLeastOnce()).findByAge(age);
        assertEquals(1, joao.size());
        assertNotNull(joao);
    }

    @Test
    public void shouldReturnTotalRentOfResidents() {
        when(residentRepository.getTotalRentResidents()).thenReturn(2000.0);
        Double totalRent = residentService.getTotalRentResidents();
        verify(residentRepository, atLeastOnce()).getTotalRentResidents();
        assertEquals(2000.0, totalRent);
    }

    @Test
    public void shouldReturnMostValuableResident() {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        when(residentRepository.getMostValuableResident()).thenReturn(resident);
        Resident joao = residentService.getMostValuableResident();
        verify(residentRepository, atLeastOnce()).getMostValuableResident();
        assertEquals(resident, joao);
    }
}
