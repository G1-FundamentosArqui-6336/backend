package org.upc.cobox.delivery.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.application.internal.outboundservices.ExternalFleetService;
import org.upc.cobox.fleet.interfaces.acl.FleetContextFacade;

import java.math.BigDecimal;

@Service
public class ExternalFleetServiceImpl implements ExternalFleetService {

    private final FleetContextFacade fleetContextFacade;

    public ExternalFleetServiceImpl(FleetContextFacade fleetContextFacade) {
        this.fleetContextFacade = fleetContextFacade;
    }

    public boolean existsFleetByIdAndCapacity(Long fleetId, BigDecimal capacity) {
        return fleetContextFacade.verifyFleet(fleetId,capacity);
    }

}
