package com.javaspringtask3StockMarketTracking.StockMarketTracking.controller;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryGetRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stockhistory")
public class StockHistoryController {
    private final StockHistoryService service;

    public StockHistoryController(StockHistoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StockHistoryDto> getStockLatestHistory(@RequestBody StockHistoryGetRequest stockHistoryGetRequest){
        return ResponseEntity.ok(service.getLatestStockHistory(stockHistoryGetRequest));
    }
}
