package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryDto {
    private int StockHistoryID;

    private long previousValue;
    private long currentValue;
}
