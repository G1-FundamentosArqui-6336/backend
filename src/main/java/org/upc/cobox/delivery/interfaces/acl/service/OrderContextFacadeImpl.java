package org.upc.cobox.delivery.interfaces.acl.service;

import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.domain.model.commands.MarkAsInTransitOrderCommand;
import org.upc.cobox.delivery.domain.model.queries.GetOrderByIdQuery;
import org.upc.cobox.delivery.domain.services.OrderCommandService;
import org.upc.cobox.delivery.domain.services.OrderQueryService;
import org.upc.cobox.delivery.interfaces.acl.OrderContextFacade;

@Service
public class OrderContextFacadeImpl implements OrderContextFacade
{
    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public OrderContextFacadeImpl(OrderCommandService orderCommandService,OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
    }

    public Double fetchWeightOrderByOrderId(Long orderId) {
        var getOrderById = new GetOrderByIdQuery(orderId);
        var order = orderQueryService.handle(getOrderById);
        if (order.isEmpty()) return null;
        return order.get().getWeightValue();
    }

    public  Boolean existsOrderByOrderId(Long orderId){
        var getOrderById = new GetOrderByIdQuery(orderId);
        var order = orderQueryService.handle(getOrderById);
        return order.isPresent();
    }

    public void  markAsInTransitOrder(Long orderId){
        var markAsInTransitOrderCommand = new MarkAsInTransitOrderCommand(orderId);
        orderCommandService.handle(markAsInTransitOrderCommand);
    }

}
