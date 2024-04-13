package com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request;

public class StockHistoryAddRequest {
    int stockId;
    int currentValue;

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
