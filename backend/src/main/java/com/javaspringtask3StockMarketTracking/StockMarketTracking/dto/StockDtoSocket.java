package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDtoSocket {

    private int stockID;
    private String name;

    private List<StockHistoryDtoSocket> stockHistory;

}