package com.javaspringtask3StockMarketTracking.StockMarketTracking.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDto;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.StockDtoSocket;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockDtoConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockDtoSocketConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.converter.StockHistoryDtoSocketConverter;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.dto.request.StockHistoryAddRequest;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockHistoryService;
import com.javaspringtask3StockMarketTracking.StockMarketTracking.service.StockService;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SocketModule {

    private final SocketIOServer socketIOServer;
    private final StockService stockService;
    private final StockDtoConverter converter;
    private final StockHistoryService stockHistoryService;
    private final StockHistoryDtoSocketConverter stockHistoryDtoSocketConverter;
    private final StockDtoSocketConverter stockDtoSocketConverter;

    public SocketModule(SocketIOServer socketIOServer, StockService stockService, StockDtoConverter converter, StockHistoryService stockHistoryService, StockHistoryDtoSocketConverter stockHistoryDtoSocketConverter, StockDtoSocketConverter stockDtoSocketConverter) {
        this.socketIOServer = socketIOServer;
        this.stockService = stockService;
        this.converter = converter;
        this.stockHistoryService = stockHistoryService;
        this.stockHistoryDtoSocketConverter = stockHistoryDtoSocketConverter;
        this.stockDtoSocketConverter = stockDtoSocketConverter;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_stock_history", StockHistoryAddRequest.class, onStockHistoryReceived());
        socketIOServer.addEventListener("send_new_stock", StockAddRequest.class, onStockReceived());

    }
    private DataListener<StockAddRequest> onStockReceived() {
        return (senderClient, data, ackSender) -> {
            StockDto newStockDto = stockService.addNewStock(data);
            sendAllStocks(senderClient, "all");
            ackSender.sendAckData("Stock added successfully", newStockDto);

        };
    }

    private DataListener<StockHistoryAddRequest> onStockHistoryReceived() {
        return (senderClient, data, ackSender) -> {
            StockDto newStockDto = stockService.saveStockHistory(data);

            sendAllStocks(senderClient, "all");
            sendSingleStock(senderClient, newStockDto.getName());
            ackSender.sendAckData("Stock history added successfully", newStockDto);

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
        }else {
            sendSingleStock(client, room);
        }
    }

    private void sendAllStocks(SocketIOClient client, String room) {
        List<StockDtoSocket> allStocks = stockService.getAllStock().stream().map(stockDto -> stockDtoSocketConverter.convert(stockDto)).collect(Collectors.toList());
        client.getNamespace().getRoomOperations(room)
                .sendEvent("get_all_stock", allStocks, room);
    }

    private void sendSingleStock(SocketIOClient client, String room) {
        StockDto stockDto = stockService.getStockByName(room.toString());
        Map<String, StockDtoSocket> stockHistoryMap = stockService.getStockForAllIntervals(stockDto.getStockID());
        StockDtoSocket stockDtos = stockHistoryMap.get("yearly");
        client.getNamespace().getRoomOperations(room).sendEvent("get_single_stock_yearly",stockDtos);
        client.getNamespace().getRoomOperations(room).sendEvent("get_single_stock_monthly",stockHistoryMap.get("monthly"));
        client.getNamespace().getRoomOperations(room).sendEvent("get_single_stock_weekly",stockHistoryMap.get("weekly"));
    }
    private void sendConnectMessage(SocketIOClient client, String room) {
        System.out.println("connected");
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
        Slice<StockDto> allStocks = stockService.getAllStockPagination();
        client.sendEvent("all_stocks", allStocks);
    }

    private void updateSingleStock(SocketIOClient client, int stockName) {
        StockDto stockDto = converter.convert(stockService.getStockById(stockName));
        client.sendEvent("single_stock", stockDto);
    }

}