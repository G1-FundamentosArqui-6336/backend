package org.upc.cobox.delivery.domain.model.events;

import java.util.Date;

public record OrderDeliveredEvent(Long orderId, Long clientId, Date deliveredAt) {}
