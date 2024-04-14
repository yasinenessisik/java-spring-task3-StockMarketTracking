package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.corundumstudio.socketio.SocketIOServer;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class Application implements ApplicationRunner {

    private final StockService stockService;
    private final SocketIOServer server;

    public Application(StockService stockService, SocketIOServer server) {
        this.stockService = stockService;
        this.server = server;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        server.start();

        Random random = new Random();
        LocalDateTime now = LocalDateTime.now(); // Şu anki tarih ve zaman
        LocalDateTime oneYearAgo = now.minusYears(1); // Bir yıl önceki tarih ve zaman

        // 3 farklı hisse senedi oluştur
        StockAddRequest stock1 = new StockAddRequest();
        stock1.setName("Google");

        StockAddRequest stock2 = new StockAddRequest();
        stock2.setName("Apple");

        StockAddRequest stock3 = new StockAddRequest();
        stock3.setName("Microsoft");

        stockService.addNewStock(stock1);
        stockService.addNewStock(stock2);
        stockService.addNewStock(stock3);

        // Her bir hisse senedi için 20 farklı geçmiş verisi oluştur
        for (int i = 1; i <= 20; i++) {
            // Google hissesi için geçmiş veri oluştur
            StockHistoryAddRequest history1 = new StockHistoryAddRequest();
            history1.setStockId(1);
            history1.setCurrentValue(random.nextInt(1000) + 1); // 1 ile 1000 arasında rastgele bir değer
            history1.setLocalDateTime(generateRandomDateTime(oneYearAgo, now, random)); // Rastgele tarih ekle
            stockService.saveStockHistory(history1);

            // Apple hissesi için geçmiş veri oluştur
            StockHistoryAddRequest history2 = new StockHistoryAddRequest();
            history2.setStockId(2);
            history2.setCurrentValue(random.nextInt(1000) + 1);
            history2.setLocalDateTime(generateRandomDateTime(oneYearAgo, now, random)); // Rastgele tarih ekle
            stockService.saveStockHistory(history2);

            // Microsoft hissesi için geçmiş veri oluştur
            StockHistoryAddRequest history3 = new StockHistoryAddRequest();
            history3.setStockId(3);
            history3.setCurrentValue(random.nextInt(1000) + 1);
            history3.setLocalDateTime(generateRandomDateTime(oneYearAgo, now, random)); // Rastgele tarih ekle
            stockService.saveStockHistory(history3);

        }

    }

    private LocalDateTime generateRandomDateTime(LocalDateTime minDate, LocalDateTime maxDate, Random random) {
        long minDay = minDate.toLocalDate().toEpochDay();
        long maxDay = maxDate.toLocalDate().toEpochDay();
        long randomDay = minDay + random.nextInt((int) (maxDay - minDay));
        long randomSecond = random.nextInt(24 * 60 * 60);
        return LocalDateTime.ofEpochSecond(randomDay * 24 * 60 * 60 + randomSecond, 0, java.time.ZoneOffset.UTC);
    }
}
