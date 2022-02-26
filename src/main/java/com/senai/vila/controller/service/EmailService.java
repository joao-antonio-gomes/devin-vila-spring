package com.senai.vila.controller.service;

import com.senai.vila.model.dto.ResidentDto;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage message);

    void sendNewPassword(ResidentDto user, String newPassword);

}
