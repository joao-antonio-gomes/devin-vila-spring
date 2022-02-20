package com.senai.vila.model.entity;

import com.senai.vila.model.dto.FinancialReportDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "financial_reports")
public class FinancialReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "budget_remaining")
    private Double budgetRemaining;
    @Column(name = "budget_spent")
    private Double budgetSpent;
    @Column(name = "created_at")
    private LocalDate createdAt;

    public FinancialReport(Long id, Double budgetRemaining, Double budgetSpent, Resident mostExpensiveResident) {
        this.id = id;
        this.budgetRemaining = budgetRemaining;
        this.budgetSpent = budgetSpent;
        this.mostExpensiveResident = mostExpensiveResident;
        this.createdAt = LocalDate.now();
    }

    public FinancialReport(Double budgetRemaining, Double budgetSpent, Resident mostExpensiveResident) {
        this.budgetRemaining = budgetRemaining;
        this.budgetSpent = budgetSpent;
        this.mostExpensiveResident = mostExpensiveResident;
        this.createdAt = LocalDate.now();
    }

    public FinancialReport() {
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

    public void setMostExpensiveResident(Resident mostExpensiveResident) {
        this.mostExpensiveResident = mostExpensiveResident;
    }

    @ManyToOne
    @JoinColumn(name = "most_expensive_resident_id")
    private Resident mostExpensiveResident;

    public Resident getMostExpensiveResident() {
        return mostExpensiveResident;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinancialReportDto convertToDto() {
        return new FinancialReportDto(budgetRemaining, budgetSpent, mostExpensiveResident.convertToDto());
    }
}
