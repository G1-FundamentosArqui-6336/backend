package org.upc.cobox.delivery.interfaces.acl;



public interface OrderContextFacade {
     Double fetchWeightOrderByOrderId(Long orderId);
     Boolean existsOrderByOrderId(Long orderId);
     void markAsInTransitOrder(Long orderId);
}
