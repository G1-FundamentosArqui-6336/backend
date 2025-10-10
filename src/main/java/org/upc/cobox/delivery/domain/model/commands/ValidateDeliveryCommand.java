package org.upc.cobox.delivery.domain.model.commands;

import java.util.Date;

public record ValidateDeliveryCommand(Long orderId,
                                      String receiverName,
                                      String photoUrl,
                                      String signatureCode,
                                      Date takenAt) {
}
