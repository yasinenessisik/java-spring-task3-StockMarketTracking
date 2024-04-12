package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import org.springframework.stereotype.Component;

@Component
public class StockHistoryDtoConverter {
    public StockHistoryDto convert(StockHistory from){
        return StockHistoryDto.builder()
                .StockHistoryID(from.getStockHistoryID())
                .currentValue(from.getCurrentValue())
                .changeDirection(from.getChangeDirection().toString())
                .percentageChange(from.getPercentageChange())
                .localDateTime(from.getLocalDateTime().toString())
                .build();
    }
}
