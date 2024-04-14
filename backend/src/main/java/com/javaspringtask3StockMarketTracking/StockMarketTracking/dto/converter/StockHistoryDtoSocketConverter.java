package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter;


import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDtoSocket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class StockHistoryDtoSocketConverter {
    public StockHistoryDtoSocket convert(StockHistoryDto from){
        return StockHistoryDtoSocket.builder()
                .stockHistoryID(from.getStockHistoryID())
                .currentValue(from.getCurrentValue())
                .changeDirection(from.getChangeDirection().toString())
                .percentageChange(from.getPercentageChange())
                .localDateTime(convertToUnixTimestamp(from.getLocalDateTime()))
                .build();
    }
    public long convertToUnixTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
