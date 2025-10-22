package org.upc.cobox.delivery.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.domain.exceptions.EvidenceNotFoundException;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.commands.CreateOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.MarkAsCompletedOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.MarkAsInTransitOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.MarkAsReadyForDispatchOrderCommand;
import org.upc.cobox.delivery.domain.services.OrderCommandService;
import org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories.EvidenceRepository;
import org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories.OrderRepository;
import org.upc.cobox.fleet.domain.exceptions.OrderNotFoundException;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final EvidenceRepository evidenceRepository;
    public OrderCommandServiceImpl(OrderRepository orderRepository,EvidenceRepository evidenceRepository) {
        this.orderRepository = orderRepository;
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public Long handle(CreateOrderCommand command) {
        var order = new Order(command);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public void handle(MarkAsInTransitOrderCommand command) {
        orderRepository.findById(command.orderId()).map(order -> {
            order.markAsInTransit();
            orderRepository.save(order);
            return order.getId();
        }).orElseThrow(() -> new OrderNotFoundException(command.orderId()));
    }

    @Override
    public void handle(MarkAsReadyForDispatchOrderCommand command) {
        orderRepository.findById(command.orderId()).map(order -> {
            order.markAsReadyForDispatch();
            orderRepository.save(order);
            return order.getId();
        }).orElseThrow(() -> new OrderNotFoundException(command.orderId()));
    }

    @Override
    public void handle(MarkAsCompletedOrderCommand command) {
        orderRepository.findById(command.orderId()).map(order -> {
            var evidence = evidenceRepository.findById(command.evidenceId());
            if(evidence.isEmpty()) throw new EvidenceNotFoundException(command.evidenceId());
            order.markAsCompletedDelivery(evidence.get(), command.routeId());
            orderRepository.save(order);
            return order.getId();
        }).orElseThrow(() -> new OrderNotFoundException(command.orderId()));
    }

}
