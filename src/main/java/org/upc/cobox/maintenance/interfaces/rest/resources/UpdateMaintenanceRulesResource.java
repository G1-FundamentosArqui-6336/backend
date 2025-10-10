package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateMaintenanceRulesResource(
        @NotNull Long scheduleId,
        @Size(min = 1) List<MaintenanceRuleResource> rules
) {}
