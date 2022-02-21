package com.senai.vila.utils;

import com.senai.vila.exception.CpfException;
import com.senai.vila.exception.PasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PasswordValidatorTest {

    @Test
    public void shouldThrowErrorWhenPasswordLengthIsMinorThanEight() {
        // Given
        String password = "senha";

        // When
        // Then
        assertThrows(PasswordException.class, () -> PasswordValidator.validatePassword(password));
    }

    @Test
    public void shouldThrowErrorWhenPasswordDidntHaveDigit() {
        // Given
        String password = "senhaGrande!";

        // When
        // Then
        assertThrows(PasswordException.class, () -> PasswordValidator.validatePassword(password));
    }

    @Test
    public void shouldThrowErrorWhenPasswordDidntHaveUppercase() {
        // Given
        String password = "senhagrande!1";

        // When
        // Then
        assertThrows(PasswordException.class, () -> PasswordValidator.validatePassword(password));
    }

    @Test
    public void shouldThrowErrorWhenPasswordDidntHaveLowercase() {
        // Given
        String password = "SENHAGRANDE1!";

        // When
        // Then
        assertThrows(PasswordException.class, () -> PasswordValidator.validatePassword(password));
    }

    @Test
    public void shouldThrowErrorWhenPasswordDidntHaveSpecialCharacter() {
        // Given
        String password = "senhaGrande1";

        // When
        // Then
        assertThrows(PasswordException.class, () -> PasswordValidator.validatePassword(password));
    }

    @Test
    public void shouldValidatePassword() throws CpfException, PasswordException {
        // Given
        String password = "senhaGrande1!";

        // When
        // Then
        assertEquals(password, PasswordValidator.validatePassword(password));
    }
}
