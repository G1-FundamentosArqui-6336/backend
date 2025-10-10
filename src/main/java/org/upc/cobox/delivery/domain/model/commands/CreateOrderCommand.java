package org.upc.cobox.delivery.domain.model.commands;

import java.math.BigDecimal;
import java.util.Date;

public record CreateOrderCommand (Long clientId,
                                  String addressLine,
                                  String city,
                                  String country,
                                  String postalCode,
                                  String reference,
                                  Date scheduledAt,
                                  String notes,
                                  BigDecimal totalWeight){
}
