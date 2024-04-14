package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request;

import java.time.LocalDateTime;

public class StockHistoryAddRequest {
    int stockId;
    int currentValue;
    LocalDateTime localDateTime;


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

}
