package org.upc.cobox.fleet.domain.model.commands;

import java.math.BigDecimal;

public record CreateVehicleCommand(String plateNumber,Double capacityKg) {}
