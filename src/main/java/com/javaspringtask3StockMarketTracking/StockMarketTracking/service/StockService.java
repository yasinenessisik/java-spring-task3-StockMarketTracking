package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockDtoConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.exception.ErrorCode;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.exception.GenericExceptionHandler;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final SocketIOServer socketIOServer;
    private final StockRepository stockRepository;
    private final StockDtoConverter converter;
    public StockService(SocketIOServer socketIOServer, StockRepository stockRepository, StockDtoConverter converter) {
        this.socketIOServer = socketIOServer;
        this.stockRepository = stockRepository;
        this.converter = converter;
    }
    @Transactional
    public Stock getStockById(int id){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> GenericExceptionHandler.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorCode(ErrorCode.STOCK_NOT_FOUND)
                        .errorMessage("Stock Not Found")
                        .build());
        return stock;
    }
    public StockDto findStokById(int id){
        return converter.convert(getStockById(id));
    }

    protected Stock updateStockHistory(Stock stock) {
        try {
            Stock stock1 = stockRepository.save(stock);
            return stock1;
        }catch (Exception exception){
            throw GenericExceptionHandler.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorCode(ErrorCode.ERROR_WHILE_UPDATING)
                    .errorMessage("Error occurs while update Stock")
                    .build();
        }
    }
    @Transactional
    public StockDto saveStockHistory(StockHistoryAddRequest from){
        Stock stock = getStockById(from.getStockId());

        StockHistory history2 = new StockHistory();
        history2.setPreviousValue(from.getPreviousValue());
        history2.setCurrentValue(from.getCurrentValue());
        history2.setStock(stock);

        stock.getStockHistory().add(history2);
        return converter.convert(updateStockHistory(stock));
    }

    public StockDto addNewStock(StockAddRequest from){
        Stock stock = new Stock();
        stock.setName(from.getName());
        stock.setStockHistory(new HashSet<>());
        return converter.convert(stockRepository.save(stock));
    }
    public List<StockDto> getAllStock(){
        return stockRepository.findAll().stream().map(stock -> converter.convert(stock)).collect(Collectors.toList());
    }
}
