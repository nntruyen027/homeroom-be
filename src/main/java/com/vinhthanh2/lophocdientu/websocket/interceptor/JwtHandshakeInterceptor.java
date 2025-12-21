package com.vinhthanh2.lophocdientu.websocket.interceptor;

import com.vinhthanh2.lophocdientu.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        String token = extractToken(request);
        if (token == null) return false;

        try {
            String username = jwtService.extractUsername(token);
            attributes.put("username", username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String extractToken(ServerHttpRequest request) {

        // Query param: /ws?token=xxx
        if (request instanceof org.springframework.http.server.ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");
            if (token != null && !token.isBlank()) {
                return token;
            }
        }

        // Authorization header
        var auth = request.getHeaders().getFirst("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7);
        }

        return null;
    }
}
