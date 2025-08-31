package com.backend.api.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.api.websocket.StreamPublisher;
import com.backend.api.websocket.StreamPublisher.DecisionEvent;

@Service
public class RedisPublisher {
    private final StringRedisTemplate redis;

    public RedisPublisher(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void publishDecision(StreamPublisher.DecisionEvent e) {
        redis.convertAndSend("transactions", toJson(e));
    }

    private String toJson(StreamPublisher.DecisionEvent e) {
        return String.format(
                "{\"id\":\"%s\",\"mcc\":\"%s\",\"amountCents\":%d,\"decision\":\"%s\",\"ts\":\"%s\"}",
                e.id(), e.mcc(), e.amountCents(), e.decision(), e.ts().toString());
    }
}
