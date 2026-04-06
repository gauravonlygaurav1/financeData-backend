package com.example.financeData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;
}
