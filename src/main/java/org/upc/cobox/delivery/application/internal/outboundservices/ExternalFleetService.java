package org.upc.cobox.delivery.application.internal.outboundservices;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ExternalFleetService {
    public boolean existsFleetByIdAndCapacity(Long fleetId, BigDecimal capacity);
}
