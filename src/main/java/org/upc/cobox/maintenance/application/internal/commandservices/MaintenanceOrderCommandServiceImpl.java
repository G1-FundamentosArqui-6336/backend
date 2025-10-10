package org.upc.cobox.maintenance.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.commands.*;
import org.upc.cobox.maintenance.domain.model.entities.Job;
import org.upc.cobox.maintenance.domain.model.entities.PartsRequest;
import org.upc.cobox.maintenance.domain.model.valueobjects.*;
import org.upc.cobox.maintenance.domain.services.MaintenanceOrderCommandService;
import org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceOrderCommandServiceImpl implements MaintenanceOrderCommandService {

    private final MaintenanceOrderRepository orderRepository;

    public MaintenanceOrderCommandServiceImpl(MaintenanceOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<MaintenanceOrder> handle(CreateMaintenanceOrderCommand command) {
        var vehicleId = new VehicleId(command.vehicleId());
        if (orderRepository.existsOpenOrderByVehicleId(vehicleId)) return Optional.empty();

        Timelapse maybeSchedule = parseTimelapseOrNull(command.startTime(), command.endTime());

        var order = new MaintenanceOrder(
                vehicleId,
                command.maintenanceType(),
                command.priority(),
                command.reason(),
                String.valueOf(command.openingOdometer()),
                maybeSchedule
        );
        return Optional.of(orderRepository.save(order));
    }

    @Override
    public Long handle(ScheduleMaintenanceOrderCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var tl = new Timelapse(
                parseIso(command.startTime()),
                parseIso(command.endTime())
        );

        boolean hasConflicts = hasTimeConflictsForVehicle(
                new VehicleId(command.vehicleId()),
                tl.getStartTime(),
                tl.getEndTime(),
                order.getId()
        );

        order.scheduleMaintenanceOrder(tl, hasConflicts);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(StartMaintenanceOrderCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var scheduled = order.getScheduledTimelapse();
        if (scheduled == null) {
            throw new IllegalStateException("Order has no scheduled timelapse");
        }

        boolean overlaps = hasTimeConflictsForVehicle(
                new VehicleId(command.vehicleId()),
                scheduled.getStartTime(),
                scheduled.getEndTime(),
                order.getId()
        );

        order.startMaintenanceOrder(command.technicianId(), overlaps);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(CompleteMaintenanceOrderCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.completeMaintenanceOrder(String.valueOf(command.closingOdometer()));
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(CancelMaintenanceOrderCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.cancelMaintenanceOrder(command.reason());
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(RegisterJobCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var job = new Job(command.description(), command.estimatedDuration());
        if (command.technicianId() != null) job.assignTechnician(command.technicianId());
        if (command.partNumber() != null && !command.partNumber().isBlank()) job.assignPartNumber(command.partNumber());

        order.registerJob(job);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(RequestPartsCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var pr = new PartsRequest(
                command.partNumber(),
                command.description(),
                new Quantity(command.quantity(), command.unit())
        );
        if (command.supplierId() != null) pr.assignSupplier(command.supplierId());

        order.requestParts(pr);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(ReceivePartsCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.receiveParts(command.partNumber());
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long handle(RegisterCostCommand command) {
        var order = orderRepository.findById(command.maintenanceOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.registerCost(new Money(BigDecimal.valueOf(command.amount()), command.currency()));
        orderRepository.save(order);
        return order.getId();
    }

    // --------- Helpers ---------

    private boolean hasTimeConflictsForVehicle(
            VehicleId vehicleId, LocalDateTime start, LocalDateTime end, Long excludeOrderId) {

        List<MaintenanceOrder> open = orderRepository.findOpenOrdersByVehicleId(vehicleId);
        for (var other : open) {
            if (excludeOrderId != null && excludeOrderId.equals(other.getId())) continue;
            var tl = other.getScheduledTimelapse();
            if (tl == null) continue;

            LocalDateTime s = tl.getStartTime();
            LocalDateTime e = tl.getEndTime();
            if (start.isBefore(e) && s.isBefore(end)) return true;
        }
        return false;
    }

    private static LocalDateTime parseIso(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private static Timelapse parseTimelapseOrNull(String start, String end) {
        if (start == null || end == null || start.isBlank() || end.isBlank()) return null;
        return new Timelapse(parseIso(start), parseIso(end));
    }
}
