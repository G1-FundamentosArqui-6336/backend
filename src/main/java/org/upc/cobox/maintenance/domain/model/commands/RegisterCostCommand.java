package org.upc.cobox.maintenance.domain.model.commands;

public record RegisterCostCommand(
        Long maintenanceOrderId,
        Double amount,
        String currency
) {
}
