package com.senai.vila.controller.service;

import com.senai.vila.config.VillageConfig;
import com.senai.vila.model.dto.FinancialReportDto;
import com.senai.vila.model.entity.FinancialReport;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.FinancialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialReportService {
    @Autowired
    private ResidentService residentService;

    private final FinancialReportRepository financialReportRepository;

    public FinancialReportService(FinancialReportRepository financialReportRepository) {
        this.financialReportRepository = financialReportRepository;
    }

    public FinancialReportDto getFinancialReport() {
        Double budgetSpent = residentService.getTotalRentResidents();
        Double budget = VillageConfig.getBudget();
        Double budgetRemaining = budget - budgetSpent;
        Resident mostValuableResident = residentService.getMostValuableResident();

        FinancialReport financialReport = new FinancialReport(budgetRemaining, budgetSpent, mostValuableResident);
        return financialReportRepository.save(financialReport).convertToDto();
    }
}
