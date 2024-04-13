package com.javaspringtask3StockMarketTracking.StockMarketTracking.repository;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StockRepository extends JpaRepository<Stock,Integer> {
    @Query("SELECT s FROM Stock s WHERE s.name = :sname")
    Stock findByName(@Param("sname") String sname);
    @Query("SELECT s FROM Stock s JOIN s.stockHistory sh WHERE sh.localDateTime BETWEEN :startDate AND :endDate ORDER BY sh.localDateTime ASC")
    List<Stock> findAllStocksWithHistoryOrderByDateTimeAsc(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s FROM Stock s JOIN s.stockHistory sh WHERE s.stockID = :stockId AND sh.localDateTime BETWEEN :startDate AND :endDate ORDER BY sh.localDateTime ASC")
    public List<Stock> findByStockIdAndDateTimeRange(@Param("stockId") int stockId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
