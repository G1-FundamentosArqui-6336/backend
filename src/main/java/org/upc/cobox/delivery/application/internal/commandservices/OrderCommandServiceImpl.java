package org.upc.cobox.delivery.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.cobox.Incident.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.application.internal.outboundservices.ExternalFleetService;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.commands.AssignVehicleToOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.CreateOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.UpdateOrderStatusCommand;
import org.upc.cobox.delivery.domain.model.commands.ValidateDeliveryCommand;
import org.upc.cobox.delivery.domain.model.valueobjects.FleetId;
import org.upc.cobox.delivery.domain.services.OrderCommandService;
import org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories.OrderRepository;
import org.upc.cobox.fleet.domain.exceptions.FleetNotFoundException;

import java.util.Optional;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final ExternalFleetService externalFleetService;
    public OrderCommandServiceImpl(OrderRepository orderRepository,ExternalFleetService externalFleetService) {
        this.orderRepository = orderRepository;
        this.externalFleetService = externalFleetService;
    }

    @Override
    public Optional<Order> handle(CreateOrderCommand command) {
        Order order = new Order(command);
        Order saved = orderRepository.save(order);
        return Optional.of(saved);
    }

    @Override
    public Optional<Order> handle(UpdateOrderStatusCommand command) {
        return orderRepository.findByIdAndClientId(command.orderId(),command.clientId())
                .flatMap(existing -> {
                    boolean ok = existing.updateStatus(command);
                    if (!ok) return Optional.empty();
                    return Optional.of(orderRepository.save(existing));
                });
    }

    @Override
    public Optional<Order> handle(ValidateDeliveryCommand command) {
        return orderRepository.findById(command.orderId())
                .flatMap(existing -> {
                    Evidence evidence = new Evidence(
                            command.receiverName(),
                            command.photoUrl(),
                            command.signatureCode(),
                            command.takenAt()
                    );
                    boolean ok = existing.validateDelivery(evidence);
                    if (!ok) return Optional.empty();
                    return Optional.of(orderRepository.save(existing));
                });
    }

//    @Override
//    public Optional<Order> handle(AssignVehicleToOrderCommand command) {
//        var orderOpt = orderRepository.findById(command.orderId());
//        if (orderOpt.isEmpty()) return Optional.empty();
//
//
//        boolean fleetExists = externalFleetService.existsFleetByIdAndCapacity(command.fleetId(),orderOpt.get().getTotalWeight().getWeightKg());
//
//        if (!fleetExists) {
//            throw new FleetNotFoundException(command.fleetId());
//        }
//
//        Order order = orderOpt.get();
//        // 1) Asociar la identidad del vehículo al pedido
//        boolean ok = order.assignVehicle(new FleetId(command.fleetId()));
//        if (!ok) return Optional.empty();
//
//        orderRepository.save(order);
//
//        return Optional.of(order);
//    }
}
