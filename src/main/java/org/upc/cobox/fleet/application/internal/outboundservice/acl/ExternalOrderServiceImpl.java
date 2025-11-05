package org.upc.cobox.fleet.application.internal.outboundservice.acl;

import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.interfaces.acl.OrderContextFacade;
import org.upc.cobox.fleet.application.internal.outboundservice.ExternalOrderService;

@Service
public class ExternalOrderServiceImpl implements ExternalOrderService {
    private final OrderContextFacade orderContextFacade;

    public ExternalOrderServiceImpl(OrderContextFacade orderContextFacade) {
        this.orderContextFacade = orderContextFacade;
    }



    public Double getOrderWeightByOrderId(Long orderId) {
        var weight = orderContextFacade.fetchWeightOrderByOrderId(orderId);
        if (weight == null) return 0.00;
        return weight;
    }

    public Boolean existsOrderByOrderId(Long orderId) {
        return orderContextFacade.existsOrderByOrderId(orderId);
    }

    public void markAsInTransitOrder(Long orderId) {
        orderContextFacade.markAsInTransitOrder(orderId);
    }


}
