package org.upc.cobox.fleet.domain.model.commands;

public record UpdateFleetCommand(Long fleetId, String marca, String modelo, Integer capacidadKg) {}
