package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDtoSocket;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;
@Component
public class StockDtoSocketConverter {
    private final StockHistoryDtoSocketConverter converter;

    public StockDtoSocketConverter(StockHistoryDtoSocketConverter converter) {
        this.converter = converter;
    }


    public StockDtoSocket convert(StockDto from){
        return StockDtoSocket.builder()
                .stockID(from.getStockID())
                .name(from.getName())
                .stockHistory(from.getStockHistory().stream().map(stockHistoryDto -> converter.convert(stockHistoryDto)).collect(Collectors.toList()))
                .build();
    }

}
