package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.valueobjects.MaintenanceRule;
import org.upc.cobox.maintenance.interfaces.rest.resources.MaintenanceRuleResource;

public class MaintenanceRuleFromResourceAssembler {

    public static MaintenanceRule toValueObject(MaintenanceRuleResource r) {
        return new MaintenanceRule(
                r.criteria(),
                r.everyMonths(),
                r.everyKilometers(),
                r.tolerancePercentage(),
                r.maintenanceType(),
                r.defaultPriority()
        );
    }
}
