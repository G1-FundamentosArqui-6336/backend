package org.upc.cobox.fleet.domain.model.commands;

import java.math.BigDecimal;

public record UpdateFleetCommand(Long fleetId, String marca, String modelo, BigDecimal capacidadKg) {}
