package com.senai.vila.controller.rest;

import com.senai.vila.controller.service.HabitanteService;
import com.senai.vila.model.dto.HabitanteDto;
import com.senai.vila.model.entity.Habitante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/habitantes")
public class HabitanteController {

    private HabitanteService habitanteService;

    public HabitanteController(HabitanteService habitanteService) {
        this.habitanteService = habitanteService;
    }

    @GetMapping
    public ResponseEntity getAllHabitantes() {
        List<HabitanteDto> habitantes = habitanteService.getAllHabitantes();
        return ResponseEntity.ok(habitantes);
    }

    @PostMapping
    public String postHabitantes() {
        return "Habitantes Post";
    }
}
