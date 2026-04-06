package com.example.financeData.repository;

import com.example.financeData.entities.FinancialRecord;
import com.example.financeData.entities.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByType(RecordType type);
    List<FinancialRecord> findByCategory(String category);
    List<FinancialRecord> findByLocalDateBetween(LocalDate startDate, LocalDate endDate);
    List<FinancialRecord> findByTypeAndCategory(RecordType type, String category);

}
