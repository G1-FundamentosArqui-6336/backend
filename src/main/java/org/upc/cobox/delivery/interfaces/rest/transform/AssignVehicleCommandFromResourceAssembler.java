package org.upc.cobox.delivery.interfaces.rest.transform;

import org.upc.cobox.delivery.domain.model.commands.AssignVehicleToOrderCommand;
import org.upc.cobox.delivery.interfaces.rest.resources.AssignVehicleResource;

public class AssignVehicleCommandFromResourceAssembler {
    public static AssignVehicleToOrderCommand toCommand(Long orderId, AssignVehicleResource r) {
        return new AssignVehicleToOrderCommand(orderId, r.fleetId(), r.plannedStart());
    }
}