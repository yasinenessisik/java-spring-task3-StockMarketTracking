package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockHistoryId;

    private double dailyChangePercentage;

    @ManyToOne()
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    public StockHistory(double dailyChangePercentage, Stock stock) {
        this.dailyChangePercentage = dailyChangePercentage;
        this.stock = stock;
    }

    public int getStockHistoryId() {
        return stockHistoryId;
    }

    public void setStockHistoryId(int stockHistoryId) {
        this.stockHistoryId = stockHistoryId;
    }

    public double getDailyChangePercentage() {
        return dailyChangePercentage;
    }

    public void setDailyChangePercentage(double dailyChangePercentage) {
        this.dailyChangePercentage = dailyChangePercentage;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
