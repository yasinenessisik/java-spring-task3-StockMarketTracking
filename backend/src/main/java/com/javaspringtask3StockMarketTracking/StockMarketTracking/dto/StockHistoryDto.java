package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryDto {
    private int stockHistoryID;
    private long currentValue;
    private String changeDirection;
    private double percentageChange;
    private LocalDateTime localDateTime;
}
