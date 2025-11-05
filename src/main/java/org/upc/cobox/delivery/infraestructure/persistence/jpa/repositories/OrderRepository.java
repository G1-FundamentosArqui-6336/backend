package org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.valueobjects.ClientId;
import org.upc.cobox.delivery.domain.model.valueobjects.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(ClientId clientId);

}
