package com.javaspringtask3StockMarketTracking.StockMarketTracking;

import com.corundumstudio.socketio.SocketIOServer;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.Stock;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockHistoryRepository;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.repository.StockRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {
    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final SocketIOServer socketIOServer;

    public Application(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository, SocketIOServer socketIOServer) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        socketIOServer.start();

        Stock stock = new Stock();
        stock.setName("Google");

        StockHistory history2 = new StockHistory();
        history2.setPreviousValue(399);
        history2.setCurrentValue(500);
        history2.setStock(stock);

        StockHistory history1 = new StockHistory();
        history1.setPreviousValue(199);
        history1.setCurrentValue(600);
        history1.setStock(stock);

        stock.getStockHistory().add(history1);
        stock.getStockHistory().add(history2);

        stockRepository.save(stock);


        stockRepository.findAll().forEach(stock1 -> System.out.println(stock1.getName()));
        stockHistoryRepository.findAll().forEach(stock1 -> System.out.println(stock1.getPreviousValue()));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            socketIOServer.stop();
            System.out.println("SocketIO sunucusu durduruldu");
        }));
    }
}
