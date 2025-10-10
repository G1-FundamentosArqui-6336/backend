package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record ActivateMaintenanceScheduleResource(
        @NotNull Long scheduleId
) {}
