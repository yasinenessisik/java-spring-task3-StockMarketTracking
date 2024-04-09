package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;

public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryService(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }
}
