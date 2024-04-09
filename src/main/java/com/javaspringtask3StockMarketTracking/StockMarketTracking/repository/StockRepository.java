package com.javaspringtask3StockMarketTracking.StockMarketTracking.repository;

import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public class StockRepository extends JpaRepository<Stock,String> {
}
