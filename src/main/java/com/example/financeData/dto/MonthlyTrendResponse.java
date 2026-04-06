package com.example.financeData.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTrendResponse {

    private String month;
    private Double total;
}
