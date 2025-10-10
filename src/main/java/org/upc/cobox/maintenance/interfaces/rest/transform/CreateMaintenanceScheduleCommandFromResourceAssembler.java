package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.CreateMaintenanceScheduleCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.CreateMaintenanceScheduleResource;

public class CreateMaintenanceScheduleCommandFromResourceAssembler {

    public static CreateMaintenanceScheduleCommand toCommand(CreateMaintenanceScheduleResource r) {
        var rules = r.rules().stream()
                .map(MaintenanceRuleFromResourceAssembler::toValueObject)
                .toList();

        return new CreateMaintenanceScheduleCommand(
                r.vehicleId(),
                rules
        );
    }
}
