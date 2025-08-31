package com.backend.api.test;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.api.redis.RedisPublisher;
import com.backend.api.websocket.StreamPublisher;

@RestController
@RequestMapping("/api/test")
public class TestApi {

    private final StreamPublisher publisher;
    private final RedisPublisher redisPublisher;

    public TestApi(StreamPublisher publisher, RedisPublisher redisPublisher) {
        this.publisher = publisher;
        this.redisPublisher = redisPublisher;
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("We have launched!");
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendTest() {
        StreamPublisher.DecisionEvent e = new StreamPublisher.DecisionEvent("123", "6011", 1500, "DECLINE",
                Instant.now());
        redisPublisher.publishDecision(e);
        return ResponseEntity.accepted().build();
    }
}
