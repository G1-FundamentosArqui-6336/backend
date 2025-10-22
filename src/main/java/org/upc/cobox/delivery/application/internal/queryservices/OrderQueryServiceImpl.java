package org.upc.cobox.delivery.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.queries.*;
import org.upc.cobox.delivery.domain.model.valueobjects.ClientId;
import org.upc.cobox.delivery.domain.model.valueobjects.OrderStatus;
import org.upc.cobox.delivery.domain.services.OrderQueryService;
import org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories.OrderRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<Order> handle(GetOrdersByClientIdQuery query) {
        var clientId = new ClientId(query.clientId());
        return orderRepository.findByClientId(clientId);
    }


    @Override
    public List<Order> handle(GetAllOrdersQuery query) {
        return orderRepository.findAll();
    }
    @Override
    public Optional<Order> handle(GetOrderByIdQuery query) {
        return orderRepository.findById(query.orderId());
    }


}
