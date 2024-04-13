package com.javaspringtask3StockMarketTracking.StockMarketTracking.controller;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryGetRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/v1/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("addStockHistory")
    public ResponseEntity<StockDto> addStockHistory(@RequestBody @Valid StockHistoryAddRequest stockHistoryAddRequest){
        return ResponseEntity.ok(stockService.saveStockHistory(stockHistoryAddRequest));
    }

    @PostMapping("addStock")
    public ResponseEntity<StockDto> addStockHistory(@RequestBody @Valid StockAddRequest stockAddRequest){
        return ResponseEntity.ok(stockService.addNewStock(stockAddRequest));
    }
    @GetMapping("getAllStocks")
    public ResponseEntity<List<StockDto>> getAllStocks(){
        return ResponseEntity.ok(stockService.getAllStock());
    }

    @PostMapping("yearly")
    public ResponseEntity<List<StockDto>> getStockLatestHistoryYearly(@RequestBody StockHistoryGetRequest stockHistoryGetRequest){
        return ResponseEntity.ok(stockService.getStockHistoryYearly(stockHistoryGetRequest.getStockID()));
    }

    @PostMapping("monthly")
    public ResponseEntity<List<StockDto>> getStockLatestHistoryMonthly(@RequestBody StockHistoryGetRequest stockHistoryGetRequest){
        return ResponseEntity.ok(stockService.getStockHistoryMonthly(stockHistoryGetRequest.getStockID()));
    }

    @PostMapping("weekly")
    public ResponseEntity<List<StockDto>> getStockLatestHistoryWeekly(@RequestBody StockHistoryGetRequest stockHistoryGetRequest){
        return ResponseEntity.ok(stockService.getStockHistoryWeekly(stockHistoryGetRequest.getStockID()));
    }
}
