package com.senai.vila.controller.rest;

import com.senai.vila.controller.service.FinancialReportService;
import com.senai.vila.model.dto.FinancialReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financialReport")
public class FinancialReportController {
    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping
    public ResponseEntity getFinancialReport() {
        FinancialReportDto financialReport = financialReportService.getFinancialReport();
        return ResponseEntity.ok(financialReport);
    }
}
