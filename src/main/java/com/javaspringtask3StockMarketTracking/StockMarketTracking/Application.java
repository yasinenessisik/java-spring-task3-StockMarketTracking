package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public Application(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stock stock = new Stock();
        stock.setName("Google");

        StockHistory history2 = new StockHistory();
        history2.setPreviousValue(199);
        history2.setStock(stock);

        StockHistory history1 = new StockHistory();
        history1.setPreviousValue(199);
        history1.setStock(stock);

        stock.getStockHistory().add(history1);
        stock.getStockHistory().add(history2);

        stockRepository.save(stock);

        stockRepository.findAll().forEach(stock1 -> System.out.println(stock1.getName()));
        stockHistoryRepository.findAll().forEach(stock1 -> System.out.println(stock1.getPreviousValue()));


    }
}
