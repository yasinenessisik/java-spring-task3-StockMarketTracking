package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.AddStockRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/stock")
@RestController
public class StockController {
    private final StockService servie;

    public StockController(StockService servie) {
        this.servie = servie;
    }

    @PostMapping("/addStock")
    public ResponseEntity<StockDto> add(AddStockRequest stock){
        return ResponseEntity.ok(servie.addStock(stock));
    }


    @GetMapping("/getStocks")
    public ResponseEntity<List<StockDto>> getStocks(){
        return ResponseEntity.ok(servie.findAllStock());
    }

}
