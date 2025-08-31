package com.backend.api.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.backend.api.websocket.StreamPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class RedisSubscriber implements MessageListener {
    private final StreamPublisher ws;
    private final ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    public RedisSubscriber(StreamPublisher ws, RedisMessageListenerContainer c) {
        this.ws = ws;
        c.addMessageListener(this, new ChannelTopic("transactions"));
    }

    @Override
    public void onMessage(Message msg, byte[] pattern) {
        try {
            var e = om.readValue(msg.getBody(), StreamPublisher.DecisionEvent.class);
            ws.send(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
