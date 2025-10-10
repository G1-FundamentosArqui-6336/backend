package org.upc.cobox.maintenance.application.internal.eventhandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.upc.cobox.maintenance.domain.model.events.*;
import org.upc.cobox.maintenance.infrastructure.outbox.OutboxMessage;
import org.upc.cobox.maintenance.infrastructure.outbox.OutboxRepository;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IntegrationEventPublisher {

    private final OutboxRepository outbox;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    private void save(String type, Object payload) {
        String json = (payload instanceof String) ? (String) payload : objectMapper.writeValueAsString(payload);
        outbox.save(OutboxMessage.builder()
                .type(type)
                .payload(json)
                .occurredOn(OffsetDateTime.now())
                .status("NEW")
                .attempts(0)
                .build());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceOrderCreatedEvent e) {
        save("maintenance.order.created", Map.of(
                "orderId", e.orderId(),
                "vehicleId", e.vehicleId(),
                "maintenanceType", e.maintenanceType(),
                "priority", e.priority(),
                "reason", e.reason(),
                "ts", e.occurredOn().toString()
        ));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(PartsRequestedEvent e) {
        save("maintenance.parts.requested", Map.of(
                "orderId", e.orderId(),
                "partNumber", e.partNumber(),
                "quantity", e.quantity(),
                "ts", e.occurredOn().toString()
        ));
    }

    // añade los demás: scheduled, started, completed, cancelled, etc.
}
