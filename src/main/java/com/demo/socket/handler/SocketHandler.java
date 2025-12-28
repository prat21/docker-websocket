package com.demo.socket.handler;

import org.springframework.web.socket.*;

import java.io.IOException;
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
                    var message = "Hello from server at " + System.currentTimeMillis() + " session id " + session.getId();
                    System.out.println(message);
                    session.sendMessage(new TextMessage(message));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5, 3, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        var msg = "Received message: " + message.getPayload() + " from session id: " + session.getId();
        System.out.println(msg);
        session.sendMessage(new TextMessage(msg));
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
