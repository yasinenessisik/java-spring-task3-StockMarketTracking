package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockHistoryService;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/stockhistory")
@RestController
public class StockHistoryController {
    private final StockHistoryService servie;

    public StockHistoryController(StockHistoryService servie) {
        this.servie = servie;
    }


    @PostMapping("/addStockHistory/{id}")
    public ResponseEntity<StockDto> add(StockHistoryDto stockHistory,@PathVariable  int id){
        return ResponseEntity.ok(servie.addHistoryToStock(stockHistory,id));
    }
    @GetMapping
    public  ResponseEntity<List<StockHistoryDto>> findAll(){
        return ResponseEntity.ok(servie.findAll());
    }


}
