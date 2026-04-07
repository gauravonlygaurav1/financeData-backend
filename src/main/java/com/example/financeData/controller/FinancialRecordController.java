package com.example.financeData.controller;

import com.example.financeData.entities.FinancialRecord;
import com.example.financeData.entities.RecordType;
import com.example.financeData.services.FinancialRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    @PostMapping("/add")
    public ResponseEntity<FinancialRecord> createRecord(
            @Valid
            @RequestBody FinancialRecord record,
            HttpServletRequest request
    ){
        Long userId= (Long)request.getAttribute("userId");

        return ResponseEntity.ok(recordService.createRecord(record, userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FinancialRecord>> getAllRecords(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        return ResponseEntity.ok(recordService.getAllRecords(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialRecord> updateRecord(
            @Valid
            @PathVariable Long id,
            @RequestBody FinancialRecord record,
            HttpServletRequest request
    ){
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(recordService.updateRecord(id, record, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Long userId= (Long)request.getAttribute("userId");
        recordService.deleteRecord(id, userId);
        return ResponseEntity.ok("Record deleted successfully");
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
