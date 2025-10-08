package org.upc.cobox.fleet.domain.model.commands;

import org.upc.cobox.fleet.domain.model.valueobjects.Estado;

public record ChangeFleetStatusCommand(Long fleetId, Estado estado) {}
