package com.senai.vila.controller.rest;

import com.senai.vila.controller.service.ResidentService;
import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.entity.Resident;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    private ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @GetMapping
    public ResponseEntity getAllResidents() {
        List<ResidentDto> habitantes = residentService.getAllResidents();
        return ResponseEntity.ok(habitantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity getResidentById(Long id) {
        Resident resident = residentService.getResidentById(id);
        return ResponseEntity.ok(resident);
    }

    @PostMapping
    public ResponseEntity createResident(Resident resident) {
        Resident residentCreated = residentService.createResident(resident);
        return ResponseEntity.ok(residentCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteResident(Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateResident(Long id, Resident resident) {
        Resident residentUpdated = residentService.updateResident(id, resident);
        return ResponseEntity.ok(residentUpdated);
    }

}
