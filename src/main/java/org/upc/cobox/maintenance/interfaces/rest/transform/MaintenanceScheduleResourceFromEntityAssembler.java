package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.interfaces.rest.resources.MaintenanceRuleResource;
import org.upc.cobox.maintenance.interfaces.rest.resources.MaintenanceScheduleResource;

import java.util.List;

public class MaintenanceScheduleResourceFromEntityAssembler {

    public static MaintenanceScheduleResource toResource(MaintenanceSchedule e) {
        var rules = e.getRules() == null ? List.<MaintenanceRuleResource>of()
                : e.getRules().stream()
                .map(MaintenanceRuleResourceFromEntityAssembler::toResource)
                .toList();

        return new MaintenanceScheduleResource(
                e.getId(),
                e.getVehicleId() != null ? e.getVehicleId().vehicleId() : null,
                e.getStatus(),
                e.getLastEvaluationAt(),
                e.getNextEvaluationAt(),
                rules
        );
    }
}
