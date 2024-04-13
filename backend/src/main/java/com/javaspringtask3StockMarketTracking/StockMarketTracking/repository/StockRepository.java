package com.javaspringtask3StockMarketTracking.StockMarketTracking.repository;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Set;

public interface StockRepository extends JpaRepository<Stock,Integer> {
    @Query("SELECT s FROM Stock s WHERE s.name = :sname")
    Stock findByName(@Param("sname") String sname);
    @Query("SELECT s FROM Stock s JOIN s.stockHistory sh ORDER BY sh.StockHistoryID ASC")
    public Set<Stock> findAllStocksWithHistoryOrderByStockHistoryID();

}
