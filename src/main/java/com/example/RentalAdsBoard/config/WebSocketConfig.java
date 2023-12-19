package com.example.RentalAdsBoard.config;

import com.example.RentalAdsBoard.websocket.ServerSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.net.http.WebSocket;

@Configuration
public class WebSocketConfig {
    /**
     * 	注入ServerEndpointExporter，
     * 	这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        ServerEndpointExporter exporter = new ServerEndpointExporter();
//        exporter.setAnnotatedEndpointClasses(ServerSocket.class);

        return new ServerEndpointExporter();
    }

}
