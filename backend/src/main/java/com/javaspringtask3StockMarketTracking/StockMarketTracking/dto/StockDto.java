package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {

    private int stockID;
    private String name;

    private List<StockHistoryDto> stockHistory;

}
