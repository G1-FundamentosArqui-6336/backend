package org.upc.cobox.delivery.domain.model.commands;

import java.time.LocalDateTime;

public record AssignVehicleToOrderCommand(
        Long orderId,
        Long fleetId,
        LocalDateTime plannedStart // opcional: inicio planificado de la ruta
) {}