package com.javaspringtask3StockMarketTracking.StockMarketTracking;

public class StockHistoryAddRequest {
    int StockId;
    int previousValue;

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int stockId) {
        StockId = stockId;
    }

    public int getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(int previousValue) {
        this.previousValue = previousValue;
    }
}
