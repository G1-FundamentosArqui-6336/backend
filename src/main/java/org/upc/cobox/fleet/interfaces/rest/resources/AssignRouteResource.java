package org.upc.cobox.fleet.interfaces.rest.resources;

import java.time.LocalDateTime;
public record AssignRouteResource(Long routeId, LocalDateTime plannedStart) {}
