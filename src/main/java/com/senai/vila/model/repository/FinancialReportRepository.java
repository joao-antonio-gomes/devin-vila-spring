package com.senai.vila.model.repository;

import com.senai.vila.model.entity.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialReportRepository extends JpaRepository<FinancialReport, Long> {

}
