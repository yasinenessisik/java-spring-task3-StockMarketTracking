package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.corundumstudio.socketio.SocketIOServer;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockHistoryService;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.Random;

@SpringBootApplication
public class Application implements ApplicationRunner {
    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final SocketIOServer socketIOServer;
    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    public Application(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository, SocketIOServer socketIOServer, StockService stockService, StockHistoryService stockHistoryService) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.socketIOServer = socketIOServer;
        this.stockService = stockService;
        this.stockHistoryService = stockHistoryService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       //socketIOServer.start();

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
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {

            StockHistoryAddRequest history1 = new StockHistoryAddRequest();
            history1.setStockId(1);
            history1.setCurrentValue(random.nextInt(1000) + 1); // 1 ile 1000 arasında rastgele bir değer
            stockService.saveStockHistory(history1);


            StockHistoryAddRequest history2 = new StockHistoryAddRequest();
            history2.setStockId(2);
            history2.setCurrentValue(random.nextInt(1000) + 1);
            stockService.saveStockHistory(history2);


            StockHistoryAddRequest history3 = new StockHistoryAddRequest();
            history3.setStockId(3);
            history3.setCurrentValue(random.nextInt(1000) + 1);
            stockService.saveStockHistory(history3);
        }

        //stockRepository.findAll().forEach(stock -> System.out.println(stock.getName()));
        //stockHistoryRepository.findAll().forEach(history -> System.out.println(history.toString()));
        //Slice<StockHistory> findStockWeekly =  stockHistoryRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,"localDateTime")));
        //findStockWeekly.forEach(stockHistory -> System.out.println(stockHistory));
        /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            socketIOServer.stop();
            System.out.println("SocketIO sunucusu durduruldu");
        }));*/
    }

}
