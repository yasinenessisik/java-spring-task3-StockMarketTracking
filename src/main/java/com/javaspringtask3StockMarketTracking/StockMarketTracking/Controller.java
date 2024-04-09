package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class Controller {
    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public Controller(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<StockHistory>> get() {
        Stock stock = new Stock("Company A", "Description A",new HashSet<>());

        StockHistory history1 = new StockHistory(0.05, stock);
        StockHistory history2 = new StockHistory(-0.02, stock);
        StockHistory history3 = new StockHistory(0.03, stock);

        stock.getStockHistories().add(history1);
        stock.getStockHistories().add(history2);
        stock.getStockHistories().add(history3);

        Stock newStock = stockRepository.save(stock);

        return ResponseEntity.ok(stockHistoryRepository.findAll());
    }

    @PostMapping("add")
    public void add() {
        Stock stock = stockRepository.findById(1).orElseThrow();

        StockHistory history1 = new StockHistory(0.23, stock);
        stock.getStockHistories().add(history1);
        Stock newStock = stockRepository.save(stock);
    }

    @GetMapping("stocks")
    public ResponseEntity<List<Stock>> get2() {
        return ResponseEntity.ok(stockRepository.findAll());
    }
}