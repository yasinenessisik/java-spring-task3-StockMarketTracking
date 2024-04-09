package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int StockID;
    private String name;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<StockHistory> stockHistory = new HashSet<>();

    public int getStockID() {
        return StockID;
    }

    public void setStockID(int stockID) {
        StockID = stockID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StockHistory> getStockHistory() {
        return stockHistory;
    }

    public void setStockHistory(Set<StockHistory> stockHistory) {
        this.stockHistory = stockHistory;
    }
}
