package org.upc.cobox.fleet.interfaces.rest.resources;

import java.time.LocalDateTime;

public record RouteAssignmentResource(
        Long id, Long routeId, String status, LocalDateTime assignedAt, LocalDateTime plannedStart,
        LocalDateTime startedAt, LocalDateTime completedAt) {}
