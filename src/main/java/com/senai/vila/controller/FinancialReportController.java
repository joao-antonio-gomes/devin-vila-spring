package com.senai.vila.controller;

import com.senai.vila.service.FinancialReportService;
import com.senai.vila.model.dto.FinancialReportDto;
import com.senai.vila.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financialReport")
public class FinancialReportController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping
    public ResponseEntity getFinancialReport() {
        FinancialReportDto financialReport = financialReportService.getFinancialReport();
        financialReportService.sendoToRabbitMq(financialReport);
        return ResponseEntity.ok().build();
    }
}
