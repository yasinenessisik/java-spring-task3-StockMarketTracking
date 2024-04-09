package com.javaspringtask3StockMarketTracking.StockMarketTracking.repository;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {
}
