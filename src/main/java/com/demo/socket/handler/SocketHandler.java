package com.demo.socket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connection established with session id: " + session.getId());
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
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
