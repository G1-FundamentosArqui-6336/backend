package org.upc.cobox.maintenance.application.internal.eventhandlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.upc.cobox.maintenance.domain.model.events.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventHandlers {

    // Inyecta puertos/repos de proyecciones si tienes (ej: MaintenanceOrderReadModelRepository)
    // private final MaintenanceOrderReadModelRepository readModel;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceOrderCreatedEvent e) {
        log.info("MaintenanceOrderCreated orderId={} vehicleId={}", e.orderId(), e.vehicleId());
        // TODO: actualizar proyección, ej. readModel.save(...)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceOrderScheduledEvent e) {
        log.info("OrderScheduled orderId={} {} -> {}", e.orderId(), e.startTime(), e.endTime());
        // TODO: actualizar proyección de agenda
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceOrderStartedEvent e) {
        log.info("OrderStarted orderId={} technicianId={}", e.orderId(), e.technicianId());
        // TODO: marcar en proyección como IN_PROGRESS
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(PartsRequestedEvent e) {
        log.info("PartsRequested orderId={} part={} qty={}", e.orderId(), e.partNumber(), e.quantity());
        // TODO: proyección de partes pendientes
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(PartsReceivedEvent e) {
        log.info("PartsReceived orderId={} part={}", e.orderId(), e.partNumber());
        // TODO: actualizar proyección (stock/estado)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceOrderCompletedEvent e) {
        log.info("OrderCompleted orderId={} total={} closingOdometer={}", e.orderId(), e.totalCost(), e.closingOdometer());
        // TODO: proyección histórica, métricas
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceOrderCancelledEvent e) {
        log.info("OrderCancelled orderId={} reason={}", e.orderId(), e.reason());
        // TODO: proyección, métricas de cancelación
    }
}
