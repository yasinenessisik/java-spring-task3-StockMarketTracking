package com.javaspringtask3StockMarketTracking.StockMarketTracking.publisher;


import com.corundumstudio.socketio.SocketIOServer;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StockAddedEventListener implements ApplicationListener<StockAddedEvent> {

    private final SocketIOServer socketIOServer;

    public StockAddedEventListener(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void onApplicationEvent(StockAddedEvent event) {
        StockDto stockDto = event.getStockDto();
        String room = "enes";
        socketIOServer.getRoomOperations(room).sendEvent("stock_added", stockDto);
    }
}
