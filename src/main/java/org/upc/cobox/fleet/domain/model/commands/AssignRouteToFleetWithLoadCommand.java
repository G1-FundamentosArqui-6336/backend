package org.upc.cobox.fleet.domain.model.commands;

import java.time.LocalDateTime;

public record AssignRouteToFleetWithLoadCommand(
        Long fleetId,
        Long routeId,
        LocalDateTime plannedStart,
        Integer requiredKg
) {}