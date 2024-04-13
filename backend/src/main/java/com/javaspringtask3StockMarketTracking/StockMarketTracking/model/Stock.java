package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@ToString
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockID;
    private String name;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<StockHistory> stockHistory = new ArrayList<>();

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        stockID = stockID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockHistory> getStockHistory() {
        return stockHistory;
    }

    public void setStockHistory(List<StockHistory> stockHistory) {
        this.stockHistory = stockHistory;
    }
}
