package org.upc.cobox.maintenance.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelay {

    private final OutboxRepository outbox;
    // private final MessageBus bus; // Kafka/Rabbit/HTTP, etc.

    @Scheduled(fixedDelay = 5_000) // cada 5s
    public void publish() {
        List<OutboxMessage> batch = outbox.findTop50ByStatusOrderByOccurredOnAsc("NEW");
        for (var msg : batch) {
            try {
                // bus.publish(msg.getType(), msg.getPayload());
                log.info("Publishing integration event type={} id={}", msg.getType(), msg.getId());
                msg.setStatus("SENT");
                msg.setAttempts(msg.getAttempts() == null ? 1 : msg.getAttempts() + 1);
                outbox.save(msg);
            } catch (Exception ex) {
                log.error("Failed to publish outbox id={}", msg.getId(), ex);
                msg.setStatus("FAILED");
                msg.setAttempts(msg.getAttempts() == null ? 1 : msg.getAttempts() + 1);
                outbox.save(msg);
            }
        }
    }
}
