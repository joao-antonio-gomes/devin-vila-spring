package com.senai.vila.controller.service;

import com.senai.vila.config.VillageConfig;
import com.senai.vila.model.dto.FinancialReportDto;
import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import com.senai.vila.model.entity.FinancialReport;
import com.senai.vila.model.entity.Resident;
import com.senai.vila.model.repository.FinancialReportRepository;
import com.senai.vila.model.repository.ResidentRepository;
import com.senai.vila.service.FinancialReportService;
import com.senai.vila.service.ResidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FinancialReportServiceTest {

    private FinancialReportService financialReportService;
    private FinancialReportRepository financialReportRepository;
    private ResidentRepository residentRepository;
    private ResidentService residentService;

    @BeforeEach
    public void setUp() {
        this.residentRepository = Mockito.mock(ResidentRepository.class);
        this.residentService = new ResidentService(residentRepository);
        this.financialReportRepository = Mockito.mock(FinancialReportRepository.class);
        this.financialReportService = new FinancialReportService(financialReportRepository, residentService);
    }

    @Test
    public void shouldGenerateFinancialReport() {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.now(), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        Double budgetSpent = 1000.0;
        Double budget = VillageConfig.getBudget();
        Double budgetRemaining = budget - budgetSpent;
        FinancialReport financialReport = new FinancialReport(budgetRemaining, budgetSpent, resident);

        when(residentRepository.getMostValuableResident()).thenReturn(resident);
        when(residentRepository.getTotalRentResidents()).thenReturn(budgetSpent);
        when(financialReportRepository.save(Mockito.any(FinancialReport.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        FinancialReportDto financialReportDto = financialReportService.getFinancialReport();

        assertEquals(financialReportDto.getBudgetRemaining(), financialReport.getBudgetRemaining());
    }
}
