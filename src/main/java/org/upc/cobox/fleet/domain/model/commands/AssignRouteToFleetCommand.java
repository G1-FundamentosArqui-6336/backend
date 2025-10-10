package org.upc.cobox.fleet.domain.model.commands;

import java.time.LocalDateTime;

public record AssignRouteToFleetCommand(Long fleetId, Long routeId, LocalDateTime plannedStart) {}
