package com.senai.vila.service;

import com.senai.vila.config.VillageConfig;
import com.senai.vila.enums.RabbitMqEnums;
import com.senai.vila.model.dto.FinancialReportDto;
import com.senai.vila.model.entity.FinancialReport;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.FinancialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FinancialReportService {
    private final ResidentService residentService;
    private final FinancialReportRepository financialReportRepository;
    @Autowired
    private RabbitMqService rabbitMqService;

    public FinancialReportService(FinancialReportRepository financialReportRepository, ResidentService residentService) {
        this.financialReportRepository = financialReportRepository;
        this.residentService = residentService;
    }

    public FinancialReportDto getFinancialReport() {
        Double budgetSpent = residentService.getTotalRentResidents();
        Double budget = VillageConfig.getBudget();
        Double budgetRemaining = budget - budgetSpent;
        Resident mostValuableResident = residentService.getMostValuableResident();

        FinancialReport financialReport = new FinancialReport(budgetRemaining, budgetSpent, mostValuableResident);
        FinancialReportDto financialReportDto = financialReportRepository.save(financialReport).convertToDto();;
        Resident resident = (Resident) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        financialReportDto.setEmail(resident.getEmail());
        return financialReportDto;
    }

    public void sendoToRabbitMq(FinancialReportDto financialReport) {
        rabbitMqService.convertAndSend(RabbitMqEnums.EXCHANGE_FINANCIAL_REPORT, RabbitMqEnums.QUEUE_FINANCIAL_REPORT, financialReport);
    }
}
