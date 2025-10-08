package org.upc.cobox.fleet.domain.model.commands;

import org.upc.cobox.fleet.domain.model.valueobjects.Placa;

public record CreateFleetCommand(Placa placa, String marca, String modelo, Integer capacidadKg) {}
