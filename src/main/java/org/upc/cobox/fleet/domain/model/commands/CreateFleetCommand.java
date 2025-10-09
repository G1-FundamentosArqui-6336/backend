package org.upc.cobox.fleet.domain.model.commands;

import org.upc.cobox.fleet.domain.model.valueobjects.Placa;

import java.math.BigDecimal;

public record CreateFleetCommand(Placa placa, String marca, String modelo, BigDecimal capacidadKg) {}
