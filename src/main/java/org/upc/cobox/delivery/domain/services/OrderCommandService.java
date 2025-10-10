package org.upc.cobox.delivery.domain.services;

import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.commands.AssignVehicleToOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.CreateOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.UpdateOrderStatusCommand;
import org.upc.cobox.delivery.domain.model.commands.ValidateDeliveryCommand;

import java.util.Optional;

public interface OrderCommandService {
    Optional<Order> handle(CreateOrderCommand command);
    Optional<Order> handle(UpdateOrderStatusCommand command);
    Optional<Order> handle(ValidateDeliveryCommand command);
    Optional<Order> handle(AssignVehicleToOrderCommand command);
}