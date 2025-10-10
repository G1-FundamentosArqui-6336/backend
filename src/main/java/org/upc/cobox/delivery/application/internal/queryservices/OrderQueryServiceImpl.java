package org.upc.cobox.delivery.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.queries.*;
import org.upc.cobox.delivery.domain.model.valueobjects.DeliveryStatus;
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
    public Optional<Order> handle( GetOrderByIdAndClientId query) {
        return orderRepository.findByIdAndClientId(query.orderId(),query.clientId());
    }
    @Override
    public List<Order> handle(GetOrdersByClientIdAndStatus query) {
        if (query.status() == null || query.status().isBlank()) {
            return orderRepository.findByClientId(query.clientId());
        }
        DeliveryStatus st = DeliveryStatus.valueOf(query.status().trim().toUpperCase(Locale.ROOT));
        return orderRepository.findByClientIdAndStatus(query.clientId(), st);
    }

    @Override
    public List<Order> handle(GetOrdersByClientId query) {
        return orderRepository.findByClientId(query.clientId());
    }

    @Override
    public List<Order> handle(GetOrdersByStatus query) {
        DeliveryStatus st = DeliveryStatus.valueOf(query.status().trim().toUpperCase(Locale.ROOT));
        return orderRepository.findByStatus(st);
    }

    @Override
    public List<Order> handle(GetAllOrders query) {
        return orderRepository.findAll();
    }






}
