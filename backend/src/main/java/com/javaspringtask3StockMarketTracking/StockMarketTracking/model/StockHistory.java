package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockHistoryID;

    private long currentValue;
    @Enumerated(EnumType.STRING)
    private ChangeDirection changeDirection;
    private double percentageChange;
    private LocalDateTime localDateTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "stockID")
    private Stock stock;


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ChangeDirection getChangeDirection() {
        return changeDirection;
    }

    public void setChangeDirection(ChangeDirection changeDirection) {
        this.changeDirection = changeDirection;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }

    public int getStockHistoryID() {
        return stockHistoryID;
    }

    public void setStockHistoryID(int stockHistoryID) {
        stockHistoryID = stockHistoryID;
    }


    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
