package com.senai.vila.service;

import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.entity.Resident;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthenticationService implements UserDetailsService {

    private ResidentService residentService;
    private EmailService emailService;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(ResidentService residentService,
                                 EmailService emailService) {
        this.residentService = residentService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailService = emailService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email não pode ser nulo");
        }
        Resident resident = residentService.getResidentByEmail(email);
        if (resident == null) {
            throw new ResidentException("Habitante não encontrado com esse e-mail");
        }
        return resident;
    }

    public void sendNewPass(String email, String newPassword) throws ResidentException {
        Resident user = residentService.getResidentByEmail(email);
        if (user == null) {
            throw new ResidentException("Habitante não encontrado com esse e-mail");
        }
        residentService.updateUser(user);
        emailService.sendNewPassword(user.convertToDto(), newPassword);
    }

    public String generatePassword() {
        String password = new String(generatePassword(12));
        return passwordEncoder.encode(password);
    }

    /**
     * https://www.tutorialspoint.com/Generating-password-in-Java
     *
     * @param length
     * @return
     */
    public char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }
}
