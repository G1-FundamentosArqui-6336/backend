package org.upc.cobox.delivery.domain.model.queries;

public record GetOrdersByClientIdAndStatus (Long clientId, String status){
}
