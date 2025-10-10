package org.upc.cobox.maintenance.domain.services;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.domain.model.queries.GetActiveMaintenanceSchedulesQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleByIdQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleByVehicleIdQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleDueSoonQuery;

import java.util.List;
import java.util.Optional;

public interface MaintenanceScheduleQueryService {
    Optional<MaintenanceSchedule> handle(GetMaintenanceScheduleByIdQuery query);
    List<MaintenanceSchedule> handle(GetActiveMaintenanceSchedulesQuery query);
    List<MaintenanceSchedule> handle(GetMaintenanceScheduleDueSoonQuery query);
    Optional<MaintenanceSchedule> handle(GetMaintenanceScheduleByVehicleIdQuery query);
}
