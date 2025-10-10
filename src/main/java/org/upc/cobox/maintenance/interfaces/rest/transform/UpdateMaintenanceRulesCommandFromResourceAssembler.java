package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.UpdateMaintenanceRulesCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.UpdateMaintenanceRulesResource;

public class UpdateMaintenanceRulesCommandFromResourceAssembler {
    public static UpdateMaintenanceRulesCommand toCommand(UpdateMaintenanceRulesResource r) {
        var rules = r.rules().stream()
                .map(MaintenanceRuleFromResourceAssembler::toValueObject)
                .toList();

        return new UpdateMaintenanceRulesCommand(
                r.scheduleId(),
                rules
        );
    }
}
