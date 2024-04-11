package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StockDtoConverter {
    private final StockHistoryDtoConverter converter;


    public StockDtoConverter(StockHistoryDtoConverter converter) {
        this.converter = converter;
    }

    public StockDto convert(Stock from){
        return StockDto.builder()
                .StockID(from.getStockID())
                .name(from.getName())
                .stockHistory(from.getStockHistory().stream().map(stockHistory -> converter.convert(stockHistory)).collect(Collectors.toSet()))
                .build();
    }
}
