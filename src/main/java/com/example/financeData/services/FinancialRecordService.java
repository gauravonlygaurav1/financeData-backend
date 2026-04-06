package com.example.financeData.services;

import com.example.financeData.dto.CategoryResponse;
import com.example.financeData.dto.MonthlyTrendResponse;
import com.example.financeData.dto.SummaryResponse;
import com.example.financeData.entities.FinancialRecord;
import com.example.financeData.entities.RecordType;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordService {

    FinancialRecord createRecord(FinancialRecord record, Long userId);

    List<FinancialRecord> getAllRecords(Long userId);

    FinancialRecord updateRecord(Long id, FinancialRecord updatedRecord, Long userId);

    void deleteRecord(Long id, Long userId);

    List<FinancialRecord> filterRecords(
            RecordType type,
            String category,
            LocalDate startDate,
            LocalDate endDate,
            Long userId
    );

    SummaryResponse getSummary(Long userId);

    List<CategoryResponse> getCategorySummary(Long userId);

    List<MonthlyTrendResponse> getMonthlyTrends(Long userId);

}
