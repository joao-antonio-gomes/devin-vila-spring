package com.senai.vila.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinancialReportDto implements Serializable {
    private Long id;
    private Double budgetRemaining;
    private Double budgetSpent;
    private LocalDate createdAt;
    private ResidentDto mostExpensiveResident;
    private String email;
    private int attempts = 0;
    private boolean reportSend = false;

    public FinancialReportDto() {
    }

    public FinancialReportDto(String email) {
        this.email = email;
    }

    public FinancialReportDto(Long id, Double budgetRemaining, Double budgetSpent, ResidentDto mostExpensiveResident) {
        this.id = id;
        this.budgetRemaining = budgetRemaining;
        this.budgetSpent = budgetSpent;
        this.mostExpensiveResident = mostExpensiveResident;
        this.createdAt = LocalDate.now();
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "FinancialReportDto{" + ", budgetRemaining=" + budgetRemaining + ", budgetSpent=" + budgetSpent +
                ", createdAt=" + createdAt + ", mostExpensiveResident=" + mostExpensiveResident.toString() +
                ", email='" + email + '\'' + '}';
    }

    public boolean isReportSend() {
        return reportSend;
    }

    public void setReportSend(boolean reportSend) {
        this.reportSend = reportSend;
    }
}
