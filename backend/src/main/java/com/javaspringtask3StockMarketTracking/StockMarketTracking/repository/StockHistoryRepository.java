package com.javaspringtask3StockMarketTracking.StockMarketTracking.repository;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {
    @Query("SELECT sh FROM Stock s JOIN s.stockHistory sh WHERE s.stockID = :stockId ORDER BY sh.stockHistoryID DESC")
    public Page<StockHistory> getLatestStockHistory(@Param("stockId") int stockId, Pageable pageable);

    @Query("SELECT COUNT(sh) FROM Stock s JOIN s.stockHistory sh WHERE s.stockID = :stockId")
    public int countStockHistoriesByStockId(@Param("stockId") int stockId);

    @Query("SELECT sh FROM StockHistory sh " +
            "WHERE sh.stock.stockID = :stockId " +
            "AND sh.localDateTime BETWEEN :startTime AND :endTime " +
            "ORDER BY sh.localDateTime DESC ")
    List<StockHistory> findStockHistoryByStockIdAndTimeRange(
            @Param("stockId") int stockId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
