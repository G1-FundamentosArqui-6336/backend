package org.upc.cobox.fleet.application.internal.outboundservice;

import org.upc.cobox.delivery.domain.model.valueobjects.WeightKg;

import java.util.Optional;

public interface ExternalOrderService {
    Double getOrderWeightByOrderId(Long orderId);
    Boolean existsOrderByOrderId(Long orderId);
    void markAsInTransitOrder(Long orderId);

}
