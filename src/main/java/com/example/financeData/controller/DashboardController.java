package com.example.financeData.controller;

import com.example.financeData.dto.CategoryResponse;
import com.example.financeData.dto.MonthlyTrendResponse;
import com.example.financeData.dto.SummaryResponse;
import com.example.financeData.services.FinancialRecordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final FinancialRecordService recordService;

    @GetMapping("/summary")
    public SummaryResponse getSummary(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        return recordService.getSummary(userId);
    }

    @GetMapping("/category")
    public List<CategoryResponse> getCategorySummary(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        return recordService.getCategorySummary(userId);
    }

    @GetMapping("/trends")
    public List<MonthlyTrendResponse> getMonthlyTrends(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        return recordService.getMonthlyTrends(userId);
    }
}
