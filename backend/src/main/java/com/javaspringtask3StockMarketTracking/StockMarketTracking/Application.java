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
       socketIOServer.start();

        StockAddRequest stock= new StockAddRequest();
        stock.setName("Google");
        stockService.addNewStock(stock);

        StockHistoryAddRequest history2 = new StockHistoryAddRequest();
        history2.setStockId(1);
        history2.setCurrentValue(100);

        StockHistoryAddRequest history1 = new StockHistoryAddRequest();
        history1.setStockId(1);
        history1.setCurrentValue(100);

        stockService.saveStockHistory(history1);
        stockService.saveStockHistory(history2);




        stockRepository.findAll().forEach(stock1 -> System.out.println(stock1.getName()));
        stockHistoryRepository.findAll().forEach(stock1 -> System.out.println(stock1.toString()));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            socketIOServer.stop();
            System.out.println("SocketIO sunucusu durduruldu");
        }));
    }
}
