package com.senai.vila.controller.rest;

import com.senai.vila.controller.service.AuthenticationService;
import com.senai.vila.controller.service.TokenService;
import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.dto.MailDto;
import com.senai.vila.model.dto.TokenDto;
import com.senai.vila.model.entity.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken dataLogin = form.converter();

        try {
            Authentication authentication = authenticationManager.authenticate(dataLogin);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity forgot(@RequestBody MailDto email) throws ResidentException {
        authenticationService.sendNewPass(email.getEmail(), authenticationService.generatePassword());
        return ResponseEntity.ok().body("Uma nova senha foi enviada para o email informado.");
    }
}
