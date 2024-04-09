package com.javaspringtask3StockMarketTracking.StockMarketTracking.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.model.StockHistory;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocketModule {

    private final SocketIOServer socketIOServer;
    private final StockService stockService;

    public SocketModule(SocketIOServer socketIOServer, StockService stockService) {
        this.socketIOServer = socketIOServer;
        this.stockService = stockService;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", StockHistoryAddRequest.class, onMessageReceived());
    }

    private DataListener<StockHistoryAddRequest> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            StockDto stockDto = stockService.saveStockHistory(data);
            List<StockDto> stockDtos = stockService.getAllStock();
            String room = senderClient.getHandshakeData().getSingleUrlParam("room");
            senderClient.getNamespace().getRoomOperations(room).getClients().forEach(
                    x -> {
                        if (!x.getSessionId().equals(senderClient.getSessionId())) {
                            x.sendEvent("get_message", stockDtos);
                        }
                    }
            );
        };
    }


    private ConnectListener onConnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            client.getNamespace().getRoomOperations(room)
                    .sendEvent("get_message", String.format("%s connected to -> %s",
                            client.getSessionId(), room
                    ));
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.getNamespace().getRoomOperations(room)
                    .sendEvent("get_message", String.format("%s disconnected from -> %s",
                            client.getSessionId(), room
                    ));
        };
    }

}