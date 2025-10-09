package org.upc.cobox.delivery.domain.services;

import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface OrderQueryService {
    Optional<Order> handle(GetOrderByIdAndClientId query);
    List<Order> handle(GetOrdersByClientId query);
    List<Order> handle(GetOrdersByClientIdAndStatus query);
    List<Order> handle(GetOrdersByStatus query);
    List<Order> handle(GetAllOrders query);
}
