package com.javaspringtask3StockMarketTracking.StockMarketTracking.publisher;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import org.springframework.context.ApplicationEvent;

public class StockAddedEvent extends ApplicationEvent {
    private StockDto stockDto;

    public StockAddedEvent(Object source, StockDto stockDto) {
        super(source);
        this.stockDto = stockDto;
    }

    public StockDto getStockDto() {
        return stockDto;
    }
}
