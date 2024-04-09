package com.javaspringtask3StockMarketTracking.StockMarketTracking.controller;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("addStockHistory")
    public ResponseEntity<Stock> addStockHistory(@RequestBody StockHistoryAddRequest stockHistoryAddRequest){
        return ResponseEntity.ok(stockService.saveStockHistory(stockHistoryAddRequest));
    }
}
