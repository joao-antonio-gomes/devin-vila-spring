package com.senai.vila.controller.rest;

import com.senai.vila.controller.service.HabitanteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habitantes")
public class HabitanteRest {

    private HabitanteService habitanteService;

    public HabitanteRest(HabitanteService habitanteService) {
        this.habitanteService = habitanteService;
    }

    @GetMapping
    public String getHabitantes() {
        return "Habitantes Get";
    }

    @PostMapping
    public String postHabitantes() {
        return "Habitantes Post";
    }
}
