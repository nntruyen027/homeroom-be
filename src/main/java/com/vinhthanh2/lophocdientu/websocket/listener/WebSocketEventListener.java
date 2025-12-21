package com.vinhthanh2.lophocdientu.websocket.listener;

import com.vinhthanh2.lophocdientu.service.PresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final PresenceService presenceService;

    // =========================
    // CONNECT
    // =========================
    @EventListener
    public void handleWebSocketConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = accessor.getSessionId();
        String username = (String) Objects.requireNonNull(accessor.getUser()).getName();

        log.info("WS CONNECT | session={} | user={}", sessionId, username);

        presenceService.onConnect(username, sessionId);
    }

    // =========================
    // DISCONNECT
    // =========================
    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = accessor.getSessionId();

        log.info("WS DISCONNECT | session={}", sessionId);

        presenceService.onDisconnect(sessionId);
    }
}
