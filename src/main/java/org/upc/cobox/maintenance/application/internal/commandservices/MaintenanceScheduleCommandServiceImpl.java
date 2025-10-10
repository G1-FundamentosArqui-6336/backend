package org.upc.cobox.maintenance.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.domain.model.commands.*;
import org.upc.cobox.maintenance.domain.model.valueobjects.VehicleId;
import org.upc.cobox.maintenance.domain.services.MaintenanceScheduleCommandService;
import org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceOrderRepository;
import org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceScheduleCommandServiceImpl implements MaintenanceScheduleCommandService {

    private final MaintenanceScheduleRepository scheduleRepository;
    private final MaintenanceOrderRepository orderRepository;

    public MaintenanceScheduleCommandServiceImpl(MaintenanceScheduleRepository scheduleRepository,
                                                 MaintenanceOrderRepository orderRepository) {
        this.scheduleRepository = scheduleRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<MaintenanceSchedule> handle(CreateMaintenanceScheduleCommand command) {
        var vehicleId = new VehicleId(command.vehicleId());
        if (scheduleRepository.existsActiveByVehicleId(vehicleId)) return Optional.empty();

        var schedule = new MaintenanceSchedule(vehicleId, command.rules());
        return Optional.of(scheduleRepository.save(schedule));
    }

    @Override
    public Long handle(ActivateMaintenanceScheduleCommand command) {
        MaintenanceSchedule schedule = scheduleRepository.findById(command.maintenanceScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        schedule.activate();
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    @Override
    public Long handle(DeactivateMaintenanceScheduleCommand command) {
        MaintenanceSchedule schedule = scheduleRepository.findById(command.maintenanceScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        schedule.deactivate();
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    @Override
    public void handle(EvaluateMaintenanceScheduleCommand command) {
        MaintenanceSchedule schedule = scheduleRepository.findById(command.maintenanceScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        List<MaintenanceOrder> dueOrders =
                schedule.evaluateDueOrders(command.currentOdometer(), LocalDateTime.now());

        for (MaintenanceOrder order : dueOrders) {
            if (!orderRepository.existsOpenOrderByVehicleId(order.getVehicleId())) {
                orderRepository.save(order);
            }
        }
        scheduleRepository.save(schedule);
    }

    @Override
    public Long handle(UpdateMaintenanceRulesCommand command) {
        var schedule = scheduleRepository.findById(command.maintenanceScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        schedule.updateRules(command.rules());
        scheduleRepository.save(schedule);
        return schedule.getId();
    }
}
