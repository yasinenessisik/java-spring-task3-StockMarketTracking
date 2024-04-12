package com.javaspringtask3StockMarketTracking.StockMarketTracking.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockDtoConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SocketModule {

    private final SocketIOServer socketIOServer;
    private final StockService stockService;
    private final StockDtoConverter converter;

    public SocketModule(SocketIOServer socketIOServer, StockService stockService, StockDtoConverter converter) {
        this.socketIOServer = socketIOServer;
        this.stockService = stockService;
        this.converter = converter;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_stock_history", StockHistoryAddRequest.class, onStockHistoryReceived());
        socketIOServer.addEventListener("send_new_stock", StockAddRequest.class, onStockReceived());

    }
    private DataListener<StockAddRequest> onStockReceived() {
        return (senderClient, data, ackSender) -> {
            StockDto newStockDto = stockService.addNewStock(data);
            sendAllStocks(senderClient, "all");
        };
    }

    private DataListener<StockHistoryAddRequest> onStockHistoryReceived() {
        return (senderClient, data, ackSender) -> {
            StockDto newStockDto = stockService.saveStockHistory(data);
            sendAllStocks(senderClient, "all");
            sendSingleStock(senderClient, newStockDto.getName());
        };
    }


    // ----------------------onConneted -----------------------------------------------
    private ConnectListener onConnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            sendInitialData(client, room);
            sendConnectMessage(client, room);
        };
    }

    private void sendInitialData(SocketIOClient client, String room) {

        if ("all".equals(room)) {
            sendAllStocks(client, room);
        } else if ("admin".equals(room)) {
            sendAllStocks(client, room);
        } else {
            sendSingleStock(client, room);
        }
    }

    private void sendAllStocks(SocketIOClient client, String room) {
        Set<StockDto> allStocks = stockService.getAllStock();
        client.getNamespace().getRoomOperations(room)
                .sendEvent("get_all_stock", allStocks, room);
    }

    private void sendSingleStock(SocketIOClient client, String room) {
        StockDto stockDto = stockService.getStockByName(room.toString());
        client.getNamespace().getRoomOperations(room)
                .sendEvent("get_single_stock", stockDto, room);
    }
    private void sendConnectMessage(SocketIOClient client, String room) {
        client.getNamespace().getRoomOperations(room)
                .sendEvent("get_message", String.format("%s connected to -> %s",
                        client.getSessionId(), room
                ));
    }

    // ----------------------
    private DisconnectListener onDisconnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.getNamespace().getRoomOperations(room)
                    .sendEvent("get_message", String.format("%s disconnected from -> %s",
                            client.getSessionId(), room
                    ));
        };
    }
    private void updateAllStocks(SocketIOClient client) {
        Set<StockDto> allStocks = stockService.getAllStock();
        client.sendEvent("all_stocks", allStocks);
    }

    private void updateSingleStock(SocketIOClient client, int stockName) {
        StockDto stockDto = converter.convert(stockService.getStockById(stockName));
        client.sendEvent("single_stock", stockDto);
    }

}