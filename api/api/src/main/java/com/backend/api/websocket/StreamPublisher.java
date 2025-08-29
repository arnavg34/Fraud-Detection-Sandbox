package com.backend.api.websocket;

import java.time.Instant;


import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Component;

@Component
public class StreamPublisher {
    private final SimpMessagingTemplate ws;

    public StreamPublisher(SimpMessagingTemplate ws) {
        this.ws = ws;
    }

    public record DecisionEvent(String id, String mcc, int amountCents, String decision, Instant ts) {
    }

    public void send(DecisionEvent e) {
        ws.convertAndSend("/topic/stream", e);
    }
}