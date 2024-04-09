package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double dailyChangePercentage;
    private double dailyChangeAmount;
    private long stockVolume;
    private double stockMarketValue;
    private LocalDate lastUpdateDateTime;

    private double marketPrice;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

}
