package com.javaspringtask3StockMarketTracking.StockMarketTracking.service;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockHistoryDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockHistoryDtoConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryGetRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.exception.ErrorCode;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.exception.GenericExceptionHandler;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.ChangeDirection;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryDtoConverter stockHistoryDtoConverter;
    public StockHistoryService(StockHistoryRepository stockHistoryRepository, StockHistoryDtoConverter stockHistoryDtoConverter) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockHistoryDtoConverter = stockHistoryDtoConverter;
    }
    public StockHistoryDto getLatestStockHistory(StockHistoryGetRequest from){
        Page<StockHistory> page = stockHistoryRepository.getLatestStockHistory(from.getStockID(), PageRequest.of(0, 1));
        if (page.isEmpty()) {
            throw GenericExceptionHandler.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorCode(ErrorCode.STOCK_NOT_FOUND)
                    .errorMessage("Error occurs while searching Stock")
                    .build();
        }
        StockHistory latestHistory = page.getContent().get(0);
        return stockHistoryDtoConverter.convert(latestHistory);
    }

    public StockHistory generateStockHistory(StockHistoryAddRequest stockHistoryAddRequest){
        StockHistory stockHistory = new StockHistory();

        try {
                Page<StockHistory> previousStockHistory = stockHistoryRepository.getLatestStockHistory(stockHistoryAddRequest.getStockId(),PageRequest.of(0,1));
                StockHistory latestHistory = previousStockHistory.getContent().get(0);
                stockHistory.setCurrentValue(stockHistoryAddRequest.getCurrentValue());
                calculateChangeDirection(stockHistory,latestHistory);
                calculatePercentageChange(stockHistory,latestHistory);
                setLocalDateTime(stockHistory);
                System.out.println("buraya geldi" +stockHistory.toString());
                return stockHistory;



        }catch (Exception exception){
            throw GenericExceptionHandler.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorCode(ErrorCode.STOCK_NOT_FOUND)
                    .errorMessage("Error occurs while searching Stock")
                    .build();
        }
    }

    private void calculateChangeDirection(StockHistory stockHistory,StockHistory previousStockHistory) {
        boolean increase = stockHistory.getCurrentValue() > previousStockHistory.getCurrentValue();
        boolean decrase = stockHistory.getCurrentValue() < previousStockHistory.getCurrentValue();
        if (increase){
            stockHistory.setChangeDirection(ChangeDirection.INCREASE);
        } else if (decrase) {
            stockHistory.setChangeDirection(ChangeDirection.DECREASE);
        }
        else {
            stockHistory.setChangeDirection(ChangeDirection.NO_CHANGE);
        }
    }

    private void calculatePercentageChange(StockHistory stockHistory,StockHistory previousStockHistory) {
        if (previousStockHistory.getCurrentValue() == 0) {
            stockHistory.setPercentageChange(0.0);
        } else {
            double percentage = ((double) (stockHistory.getCurrentValue() - previousStockHistory.getCurrentValue()) / previousStockHistory.getCurrentValue()) * 100.0;
            stockHistory.setPercentageChange(percentage);
        }
    }
    private void setLocalDateTime(StockHistory stockHistory){
        stockHistory.setLocalDateTime(LocalDateTime.now());
    }
    public StockHistory getInitalStockHistory(){
            StockHistory stockHistory = new StockHistory();
            stockHistory.setLocalDateTime(LocalDateTime.now());
            stockHistory.setChangeDirection(ChangeDirection.NO_CHANGE);
            stockHistory.setCurrentValue(0);
            stockHistory.setPercentageChange(0);
        return stockHistory;
    }

    public Set<StockHistoryDto> getStockHistoryWeekly(Integer stockId){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);
        return stockHistoryRepository.findStockHistoryByStockIdAndTimeRange(stockId,oneWeekAgo,now).stream().map(stockHistory ->
                stockHistoryDtoConverter.convert(stockHistory)
                ).collect(Collectors.toSet());
    }
    public Set<StockHistoryDto> getStockHistoryMonthly(Integer stockId){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);
        return stockHistoryRepository.findStockHistoryByStockIdAndTimeRange(stockId,oneMonthAgo,now).stream().map(stockHistory ->
                stockHistoryDtoConverter.convert(stockHistory)
        ).collect(Collectors.toSet());
    }
    public Set<StockHistoryDto> getStockHistoryYearly(Integer stockId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);

        Set<StockHistoryDto> collect = stockHistoryRepository.findStockHistoryByStockIdAndTimeRange(stockId, oneYearAgo, now)
                .stream()
                .map(stockHistory -> stockHistoryDtoConverter.convert(stockHistory))
                .collect(Collectors.toSet());

        return collect;
    }

    public Map<String, Set<StockHistoryDto>> getStockHistoryForAllIntervals(Integer stockId) {
        Map<String, Set<StockHistoryDto>> historyMap = new HashMap<>();

        historyMap.put("yearly", getStockHistoryYearly(stockId));
        historyMap.put("monthly", getStockHistoryMonthly(stockId));
        historyMap.put("weekly", getStockHistoryWeekly(stockId));

        return historyMap;
    }
}
