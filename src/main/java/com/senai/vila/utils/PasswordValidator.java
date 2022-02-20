package com.senai.vila.utils;

import com.senai.vila.exception.PasswordException;

public class PasswordValidator {
    public static String validatePassword(String password) throws PasswordException {
        if (password.length() < 8) {
            throw new PasswordException("Senha muito curta, deve ter no mínimo 8 caracteres");
        }

        boolean hasNumbers = false;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasEspecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasNumbers = true;
            }
            if (Character.isUpperCase(password.charAt(i))) {
                hasUppercase = true;
            }
            if (Character.isLowerCase(password.charAt(i))) {
                hasLowercase = true;
            }
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                hasEspecialCharacter = true;
            }
        }

        if (!hasNumbers) {
            throw new PasswordException("Senha deve conter pelo menos um número");
        }

        if (!hasUppercase) {
            throw new PasswordException("Senha deve conter pelo menos uma letra maiúscula");
        }

        if (!hasLowercase) {
            throw new PasswordException("Senha deve conter pelo menos uma letra minúscula");
        }

        if (!hasEspecialCharacter) {
            throw new PasswordException("Senha deve conter pelo menos um caractere especial");
        }

        return password;
    }
}
