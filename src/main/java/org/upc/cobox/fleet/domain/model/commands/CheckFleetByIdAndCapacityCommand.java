package org.upc.cobox.fleet.domain.model.commands;

import java.math.BigDecimal;

public record CheckFleetByIdAndCapacityCommand (Long fleetId, BigDecimal requiredKg) {
}
