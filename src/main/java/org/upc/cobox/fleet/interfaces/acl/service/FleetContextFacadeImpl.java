package org.upc.cobox.fleet.interfaces.acl.service;

import org.springframework.stereotype.Service;
import org.upc.cobox.fleet.domain.model.commands.AssignRouteToFleetWithLoadCommand;
import org.upc.cobox.fleet.domain.model.commands.CheckFleetByIdAndCapacityCommand;
import org.upc.cobox.fleet.domain.model.queries.GetFleetByIdQuery;
import org.upc.cobox.fleet.domain.services.FleetCommandService;
import org.upc.cobox.fleet.domain.services.FleetQueryService;
import org.upc.cobox.fleet.interfaces.acl.FleetContextFacade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FleetContextFacadeImpl implements FleetContextFacade {
    private final FleetCommandService fleetCommandService;
    private final FleetQueryService fleetQueryService;

    public FleetContextFacadeImpl(FleetCommandService fleetCommandService, FleetQueryService fleetQueryService) {
        this.fleetCommandService = fleetCommandService;
        this.fleetQueryService= fleetQueryService;
    }

    public boolean verifyFleet(Long fleetId, BigDecimal Capacity) {
        var verifyPatientCommand = new CheckFleetByIdAndCapacityCommand(fleetId,Capacity);
        var exists = fleetCommandService.handle(verifyPatientCommand);
        //To improve
        if(!exists) {
            throw new RuntimeException("Fleet does not exist");
        }
        return exists;
    }
}
