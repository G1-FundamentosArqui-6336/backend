package org.upc.cobox.delivery.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AssignVehicleResource(Long fleetId, LocalDateTime plannedStart) {}