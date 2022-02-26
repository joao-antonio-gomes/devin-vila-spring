package com.senai.vila.controller.service;

import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.ResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private AuthenticationService authenticationServiceMock;
    private ResidentRepository residentRepository;
    private ResidentService residentService;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        this.residentService = Mockito.mock(ResidentService.class);
        this.emailService = Mockito.mock(EmailService.class);
        this.residentRepository = Mockito.mock(ResidentRepository.class);
        this.authenticationServiceMock = Mockito.mock(AuthenticationService.class);
        this.authenticationService = new AuthenticationService(residentService, emailService);
    }

    @Test
    public void shouldThrowErrorWhenEmailIsNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> {
                    authenticationService.loadUserByUsername(null);
                });
        assertEquals("Email não pode ser nulo", illegalArgumentException.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenDidntFoundEmail() {
        ResidentException exception = assertThrows(ResidentException.class, () -> {
            authenticationService.loadUserByUsername("joao");
        });
        assertEquals("Habitante não encontrado com esse e-mail", exception.getMessage());
    }

    @Test
    public void shouldReturnResidentByEmail() throws ResidentException {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        when(residentService.getResidentByEmail("joao@email.com")).thenReturn(resident);
        Resident residentReturned = (Resident) authenticationService.loadUserByUsername("joao@email.com");
        verify(residentService, atLeastOnce()).getResidentByEmail("joao@email.com");
        assertNotNull(residentReturned);
    }

    @Test
    public void shouldThrowErrorWhenTryToFindResidentByEmailAtSendingNewPassword() throws ResidentException {
        ResidentException exception = assertThrows(ResidentException.class, () -> {
            authenticationService.sendNewPass("joao@email.com", authenticationService.generatePassword());
        });
        assertEquals("Habitante não encontrado com esse e-mail", exception.getMessage());
    }

    @Test
    public void shouldSendNewPassword() throws ResidentException {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        when(residentService.getResidentByEmail("joao@email.com")).thenReturn(resident);
        String password = "123456";
        authenticationService.sendNewPass(resident.getEmail(), password);
        resident.setPassword(password);
        verify(residentService, atLeastOnce()).updateUser(resident);
    }

    @Test
    public void shouldGenerateNewPassword() {
        char[] password = authenticationService.generatePassword(12);
        assertNotNull(password);
        assertEquals(11, password.toString().length());
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        for (char c : password) {
            assertTrue(password.toString().matches("(?=.*[A-Z]).*"));
            assertTrue(password.toString().matches("(?=.*[a-z]).*"));
            assertTrue(password.toString().matches("(?=.*[0-9]).*"));
            assertTrue(password.toString().matches("(?=.*[!@#$]).*"));
        }
    }

    @Test
    public void shouldGeneratedPasswordMoreThan12Characters() {
        String password = authenticationService.generatePassword();
        assertNotNull(password);
        assertTrue(password.length() > 12);
    }
}
