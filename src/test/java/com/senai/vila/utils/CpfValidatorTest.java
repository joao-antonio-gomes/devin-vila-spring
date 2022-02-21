package com.senai.vila.utils;

import com.senai.vila.exception.CpfException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CpfValidatorTest {

    @Test
    public void shouldThrowErrorWhenCpfLengthIsMinorThanEleven() {
        // Given
        String cpf = "123456789";

        // When
        // Then
        assertThrows(CpfException.class, () -> CpfValidator.validateCpf(cpf));
    }

    @Test
    public void shouldThrowErrorWhenTenthDigitIsInvalid() {
        // Given
        String cpf = "09355872935";

        // When
        // Then
        assertThrows(CpfException.class, () -> CpfValidator.validateCpf(cpf));
    }

    @Test
    public void shouldThrowErrorWhenEleventhDigitIsInvalid() {
        // Given
        String cpf = "09355872926";

        // When
        // Then
        assertThrows(CpfException.class, () -> CpfValidator.validateCpf(cpf));

    }

    @Test
    public void shouldReturnCpfFormatted() throws CpfException {
        // Given
        String cpf = "09355872925";

        // When
        // Then
        assertEquals("093.558.729-25", CpfValidator.validateCpf(cpf));
    }
}
