package org.upc.cobox.maintenance.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.domain.model.queries.GetActiveMaintenanceSchedulesQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleByIdQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleByVehicleIdQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleDueSoonQuery;
import org.upc.cobox.maintenance.domain.model.valueobjects.VehicleId;
import org.upc.cobox.maintenance.domain.services.MaintenanceScheduleQueryService;
import org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MaintenanceScheduleQueryServiceImpl implements MaintenanceScheduleQueryService {

    private final MaintenanceScheduleRepository scheduleRepository;

    public MaintenanceScheduleQueryServiceImpl(MaintenanceScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Optional<MaintenanceSchedule> handle(GetMaintenanceScheduleByIdQuery query) {
        return scheduleRepository.findById(query.scheduleId());
    }

    @Override
    public List<MaintenanceSchedule> handle(GetActiveMaintenanceSchedulesQuery query) {
        return scheduleRepository.findActiveSchedules();
    }

    @Override
    public List<MaintenanceSchedule> handle(GetMaintenanceScheduleDueSoonQuery query) {
        return scheduleRepository.findDueSoon(LocalDateTime.parse(query.untilDate()));
    }

    @Override
    public Optional<MaintenanceSchedule> handle(GetMaintenanceScheduleByVehicleIdQuery query) {
        return scheduleRepository.findByVehicleId(new VehicleId(query.vehicleId()));
    }
}
