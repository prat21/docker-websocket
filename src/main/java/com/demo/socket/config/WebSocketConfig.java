package com.demo.socket.config;

import com.demo.socket.handler.SocketHandler;
import com.demo.socket.interceptor.CustomHandShakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getSocketHandler(), "/ws/test")
                //.addInterceptors(new HttpSessionHandshakeInterceptor(), new CustomHandShakeInterceptor())
                .setAllowedOrigins("*");
    }

    public WebSocketHandler getSocketHandler() {
        return new SocketHandler();
    }

}
