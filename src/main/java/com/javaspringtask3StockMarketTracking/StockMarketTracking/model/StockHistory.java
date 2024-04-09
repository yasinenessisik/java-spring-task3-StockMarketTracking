package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int StockHistoryID;

    private long previousValue;
    private long currentValue;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "stockID")
    private Stock stock;

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }

    public int getStockHistoryID() {
        return StockHistoryID;
    }

    public void setStockHistoryID(int stockHistoryID) {
        StockHistoryID = stockHistoryID;
    }

    public long getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(long previousValue) {
        this.previousValue = previousValue;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
