package com.javaspringtask3StockMarketTracking.StockMarketTracking.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {
    @Value("${socket-server.port}")
    private int port;
    @Value("${socket-server.host}")
    private String host;

    @Bean
    public SocketIOServer socketIOServer(){
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname(host);
        configuration.setPort(port);
        //configuration.setContext("/socket.io");

        return new SocketIOServer(configuration);
    }
}
