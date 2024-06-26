package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDtoSocket;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockDtoSocketConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockDtoConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.exception.ErrorCode;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.exception.GenericExceptionHandler;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.publisher.StockAddedEvent;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final ApplicationEventPublisher eventPublisher;

    private final StockRepository stockRepository;
    private final StockDtoConverter converter;
    private final StockDtoSocketConverter stockDtoSocketConverter;
    private final StockHistoryService stockHistoryService;
    public StockService(ApplicationEventPublisher eventPublisher, StockRepository stockRepository, StockDtoConverter converter, StockDtoSocketConverter stockDtoSocketConverter, StockHistoryService stockHistoryService) {
        this.eventPublisher = eventPublisher;
        this.stockRepository = stockRepository;
        this.converter = converter;
        this.stockDtoSocketConverter = stockDtoSocketConverter;
        this.stockHistoryService = stockHistoryService;
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

        StockHistory history = stockHistoryService.generateStockHistory(from);
        history.setStock(stock);

        stock.getStockHistory().add(history);

        return converter.convert(updateStockHistory(stock));
    }
    public StockDto addNewStock(StockAddRequest from) {
        Stock stock = new Stock();
        stock.setName(from.getName());

        StockHistory stockHistory = stockHistoryService.getInitalStockHistory();
        stockHistory.setStock(stock);

        stock.getStockHistory().add(stockHistory);

        Stock newStock = stockRepository.save(stock);

        return converter.convert(newStock);
    }
    public List<StockDto> getAllStock(){
        List<StockDto> collect = stockRepository.findAll().stream()
                .map(stock -> converter.convert(stock)).collect(Collectors.toList());
        return collect;
    }
    public Slice<StockDto> getAllStockPagination(){
        Slice<StockDto> bookSlice = stockRepository.findAll(PageRequest.of(0, 10, Sort.by("StockID"))).map(stock -> {
            return converter.convert(stock);
        });
        return bookSlice;
    }
    public StockDto getStockByName(String room) {
        try {
            Stock stock = stockRepository.findByName(room);
            return converter.convert(stock);
        }catch (Exception ex){
            throw GenericExceptionHandler.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorCode(ErrorCode.STOCK_NOT_FOUND)
                    .errorMessage("Stock Not Found")
                    .build();
        }

    }

    public StockDto getStockHistoryWeekly(Integer stockId){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);
        return converter.convert(stockRepository.findByStockIdAndDateTimeRange(stockId,oneWeekAgo,now));
    }
    public StockDto getStockHistoryMonthly(Integer stockId){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);
        return converter.convert(stockRepository.findByStockIdAndDateTimeRange(stockId,oneMonthAgo,now));

    }
    public StockDto getStockHistoryYearly(Integer stockId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);

        return converter.convert(stockRepository.findByStockIdAndDateTimeRange(stockId,oneYearAgo,now));
    }

    public Map<String, StockDtoSocket> getStockForAllIntervals(Integer stockId) {
        Map<String, StockDtoSocket> historyMap = new HashMap<>();

        historyMap.put("yearly", stockDtoSocketConverter.convert(getStockHistoryYearly(stockId)));
        historyMap.put("monthly", stockDtoSocketConverter.convert(getStockHistoryMonthly(stockId)));
        historyMap.put("weekly", stockDtoSocketConverter.convert(getStockHistoryWeekly(stockId)));

        return historyMap;
    }
}
