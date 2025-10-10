package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.valueobjects.MaintenanceRule;
import org.upc.cobox.maintenance.interfaces.rest.resources.MaintenanceRuleResource;

public class MaintenanceRuleResourceFromEntityAssembler {

    public static MaintenanceRuleResource toResource(MaintenanceRule r) {
        return new MaintenanceRuleResource(
                r.getCriteria(),
                r.getEveryMonths(),
                r.getEveryKilometers(),
                r.getTolerancePercentage(),
                r.getMaintenanceType() != null ? r.getMaintenanceType().name() : null,
                r.getDefaultPriority() != null ? r.getDefaultPriority().name() : null
        );
    }
}
