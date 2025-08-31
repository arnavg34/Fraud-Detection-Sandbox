package com.backend.api.services;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.backend.api.websocket.StreamPublisher;

@Service
public class GeneratorService {

    public final ApplicationEventPublisher eventPublisher;
    private ScheduledExecutorService scheduler;
    private final Random rnd = new Random();

    public GeneratorService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public synchronized void start(int ratePerSecond) {

        stop();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        long periodMs = Math.max(50, 1000 / Math.max(ratePerSecond, 1));
        scheduler.scheduleAtFixedRate(this::emit, 0, periodMs, TimeUnit.MILLISECONDS);
    }

    public synchronized void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
        }
    }

    private void emit() {
        String mcc = rnd.nextDouble() < 0.2 ? "6011" : "5812";
        int amount = 500 + rnd.nextInt(9500);
        String decision = "6011".equals(mcc) ? "DECLINE" : (amount > 8000 ? "REVIEW" : "APPROVE");
        var e = new StreamPublisher.DecisionEvent(
                UUID.randomUUID().toString(), mcc, amount, decision, java.time.Instant.now());
        eventPublisher.publishEvent(e);
    }
}
