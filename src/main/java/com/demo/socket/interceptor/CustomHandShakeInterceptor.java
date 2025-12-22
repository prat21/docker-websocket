package com.demo.socket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class CustomHandShakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        try {
            var auth = request.getHeaders().get("Authorization").stream().findFirst().orElseThrow(
                    () -> new IllegalArgumentException("Authorization header not found"));
            if (auth.split(" ")[1].equals("test")) {
                return true;
            } else {
                throw new IllegalArgumentException("Invalid Authorization token");
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
