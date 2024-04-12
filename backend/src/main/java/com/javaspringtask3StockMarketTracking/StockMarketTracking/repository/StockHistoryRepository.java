package com.javaspringtask3StockMarketTracking.StockMarketTracking.repository;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {
    @Query("SELECT sh FROM Stock s JOIN s.stockHistory sh WHERE s.StockID = :stockId ORDER BY sh.StockHistoryID DESC")
    public Page<StockHistory> getLatestStockHistory(@Param("stockId") int stockId, Pageable pageable);

    @Query("SELECT COUNT(sh) FROM Stock s JOIN s.stockHistory sh WHERE s.StockID = :stockId")
    public int countStockHistoriesByStockId(@Param("stockId") int stockId);

}
