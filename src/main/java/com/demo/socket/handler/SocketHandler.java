package com.demo.socket.handler;

import org.springframework.web.socket.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SocketHandler implements WebSocketHandler {
    ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    ScheduledFuture future = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        future = executor.scheduleAtFixedRate(() -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("Server time: " + System.currentTimeMillis()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5, 3, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        System.out.println("Received message: " + message.getPayload() + " from session id: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("Connection closed with session id: " + session.getId() + " and status: " + closeStatus);
        future.cancel(true);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
