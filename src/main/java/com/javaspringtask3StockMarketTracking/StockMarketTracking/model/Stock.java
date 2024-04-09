package com.javaspringtask3StockMarketTracking.StockMarketTracking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String companyName;
    private String companyDescription;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<StockHistory> stockHistories;
}
