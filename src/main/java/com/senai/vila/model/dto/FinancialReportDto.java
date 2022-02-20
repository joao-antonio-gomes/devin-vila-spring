package com.senai.vila.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinancialReportDto {
    private Long id;
    private Double budgetRemaining;
    private Double budgetSpent;
    private LocalDate createdAt;
    private ResidentDto mostExpensiveResident;

    public FinancialReportDto(Double budgetRemaining, Double budgetSpent, ResidentDto mostExpensiveResident) {
        this.budgetRemaining = budgetRemaining;
        this.budgetSpent = budgetSpent;
        this.mostExpensiveResident = mostExpensiveResident;
        this.createdAt = LocalDate.now();
    }

    public ResidentDto getMostExpensiveResident() {
        return mostExpensiveResident;
    }

    public void setMostExpensiveResident(ResidentDto mostExpensiveResident) {
        this.mostExpensiveResident = mostExpensiveResident;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBudgetRemaining() {
        return budgetRemaining;
    }

    public void setBudgetRemaining(Double budgetRemaining) {
        this.budgetRemaining = budgetRemaining;
    }

    public Double getBudgetSpent() {
        return budgetSpent;
    }

    public void setBudgetSpent(Double budgetSpent) {
        this.budgetSpent = budgetSpent;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
