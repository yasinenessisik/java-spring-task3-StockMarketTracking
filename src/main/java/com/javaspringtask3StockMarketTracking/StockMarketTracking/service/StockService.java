package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.AddStockRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockConverter stockConverter;

    public StockService(StockRepository stockRepository, StockConverter stockConverter) {
        this.stockRepository = stockRepository;
        this.stockConverter = stockConverter;
    }
    public StockDto addHistory(StockHistory from,int id){
        System.out.println("asdasdasdasdasdasd");
        Stock stock = getStock(id);
        System.out.println(stock.toString());
        stock.getStockHistories().add(from);
        Stock savedStock = stockRepository.save(stock);
        System.out.println(savedStock.toString());
        return stockConverter.convert(savedStock);
    }
    public StockDto addStock(AddStockRequest from){
        Stock stock1 = new Stock();
        stock1.setCompanyName(from.getCompanyName());
        stock1.setCompanyDescription(from.getCompanyDescription());
        stock1.setStockHistories(new HashSet<>());
        return stockConverter.convert(stockRepository.save(stock1));
    }

    public List<StockDto> findAllStock(){
        List<Stock> stocks = stockRepository.findAll();
        System.out.println(stocks);
        List<StockDto> stockDtos = stocks.stream()
                .map(stock -> stockConverter.convert(stock))
                .collect(Collectors.toList());
        return stockDtos;
    }

    protected Stock getStock(int id){
        return stockRepository.findById(String.valueOf(id)).orElseThrow();
    }

}
