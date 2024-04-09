package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockId;

    private String companyName;
    private String companyDescription;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    private Set<StockHistory> stockHistories;

    public Stock(String companyName, String companyDescription, Set<StockHistory> stockHistories) {
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.stockHistories = stockHistories;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public Set<StockHistory> getStockHistories() {
        return stockHistories;
    }

    public void setStockHistories(Set<StockHistory> stockHistories) {
        this.stockHistories = stockHistories;
    }
}
