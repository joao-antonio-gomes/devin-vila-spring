package com.senai.vila.controller.rest;

import com.senai.vila.controller.service.ResidentService;
import com.senai.vila.exception.CpfException;
import com.senai.vila.exception.ResidentException;
import com.senai.vila.model.dto.ResidentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity getResidentById(@PathVariable String id) throws ResidentException {
        ResidentDto resident = residentService.getResidentById(Long.valueOf(id));
        return ResponseEntity.ok(resident);
    }

    @PostMapping
    public ResponseEntity createResident(@RequestBody ResidentDto resident) throws CpfException, ResidentException {
        ResidentDto residentCreated = residentService.createResident(resident);
        return ResponseEntity.ok(residentCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteResident(@PathVariable String id) throws ResidentException {
        residentService.deleteResident(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listByBirthMonth")
    public ResponseEntity listByBirthMonth(@RequestParam(value = "birthMonth", required = false) Integer month) throws ResidentException {
        List<ResidentDto> residents = residentService.listByBirthMonth(month);
        return ResponseEntity.ok(residents);
    }

    @GetMapping("/listByFirstName")
    public ResponseEntity listByFirstName(@RequestParam(value = "firstName", required = false) String firstName) throws ResidentException {
        List<ResidentDto> residents = residentService.listByFirstName(firstName);
        return ResponseEntity.ok(residents);
    }

    @GetMapping("/listByLastName")
    public ResponseEntity listByLastName(@RequestParam(value = "lastName", required = false) String lastName) throws ResidentException {
        List<ResidentDto> residents = residentService.listByLastName(lastName);
        return ResponseEntity.ok(residents);
    }

    @GetMapping("/listByAge")
    public ResponseEntity listByAge(@RequestParam(value = "age", required = false) Integer age) throws ResidentException {
        List<ResidentDto> residents = residentService.listByAge(age);
        return ResponseEntity.ok(residents);
    }
}
