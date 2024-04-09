package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    public Stock getStockById(int id){
        return stockRepository.findById(id).get();
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }
    public Stock saveStockHistory(StockHistoryAddRequest from){
        Stock stock = getStockById(from.getStockId());

        StockHistory history2 = new StockHistory();
        history2.setPreviousValue(from.getPreviousValue());
        history2.setStock(stock);

        stock.getStockHistory().add(history2);
        return saveStock(stock);
    }

}
