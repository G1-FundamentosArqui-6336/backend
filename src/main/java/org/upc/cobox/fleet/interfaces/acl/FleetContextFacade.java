package org.upc.cobox.fleet.interfaces.acl;



import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FleetContextFacade {

    public boolean  verifyFleet(Long fleetId, BigDecimal Capacity);
}
