package org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.valueobjects.DeliveryStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndClientId(Long orderId, Long clientId);
    Optional<Order> findById(Long orderId);
    List<Order> findByClientId(Long clientId);
    List<Order> findByClientIdAndStatus(Long clientId, DeliveryStatus status);
    List<Order> findByStatus(DeliveryStatus status);
    boolean existsByIdAndStatusAndEvidenceIsNotNull(Long id, DeliveryStatus status);


}
