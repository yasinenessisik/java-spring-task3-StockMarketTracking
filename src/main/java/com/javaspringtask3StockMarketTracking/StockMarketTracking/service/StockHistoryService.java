package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockHistoryConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryConverter stockHistoryConverter;
    private final StockService stockService;

    public StockHistoryService(StockHistoryRepository stockHistoryRepository, StockHistoryConverter stockHistoryConverter, StockService stockService) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockHistoryConverter = stockHistoryConverter;
        this.stockService = stockService;
    }

    public StockDto addHistoryToStock(StockHistoryDto stockHistoryDto, int id) {

        StockHistory stockHistory = new StockHistory();

        stockHistory.setDailyChangePercentage(stockHistoryDto.getDailyChangePercentage());
        stockHistory.setDailyChangeAmount(stockHistoryDto.getDailyChangeAmount());
        stockHistory.setStockVolume(stockHistoryDto.getStockVolume());
        stockHistory.setStockMarketValue(stockHistoryDto.getStockMarketValue());
        stockHistory.setLastUpdateDateTime(stockHistoryDto.getLastUpdateDateTime());
        stockHistory.setMarketPrice(stockHistoryDto.getMarketPrice());
        return stockService.addHistory(stockHistory,id);
    }

    public List<StockHistoryDto> findAll() {
        return stockHistoryRepository.findAll().stream().map(x -> stockHistoryConverter.convert(x)).collect(Collectors.toList());
    }
}
