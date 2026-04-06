package com.example.financeData.controller;

import com.example.financeData.entities.FinancialRecord;
import com.example.financeData.entities.RecordType;
import com.example.financeData.services.FinancialRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    @PostMapping("/add")
    public FinancialRecord createRecord(
            @Valid
            @RequestBody FinancialRecord record,
            HttpServletRequest request
    ){
        Long userId= (Long)request.getAttribute("userId");

        return recordService.createRecord(record, userId);
    }

    @GetMapping("/all")
    public List<FinancialRecord> getAllRecords(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        return recordService.getAllRecords(userId);
    }

    @PutMapping("/{id}")
    public FinancialRecord updateRecord(
            @Valid
            @PathVariable Long id,
            @RequestBody FinancialRecord record,
            HttpServletRequest request
    ){
        Long userId = (Long) request.getAttribute("userId");
        return recordService.updateRecord(id, record, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Long userId= (Long)request.getAttribute("userId");
        recordService.deleteRecord(id, userId);
    }

    @GetMapping("/filter")
    public List<FinancialRecord> filterRecords(
            @RequestParam(required= false) RecordType type,
            @RequestParam(required= false) String category,
            @RequestParam(required= false) String startDate,
            @RequestParam(required= false) String endDate,
            HttpServletRequest request
    ){
        Long userId = (Long) request.getAttribute("userId");
        LocalDate start= (startDate != null) ? LocalDate.parse(startDate) : null;
        LocalDate end= (endDate != null) ? LocalDate.parse(endDate) : null;

        return recordService.filterRecords(type, category, start, end, userId);
    }
}
