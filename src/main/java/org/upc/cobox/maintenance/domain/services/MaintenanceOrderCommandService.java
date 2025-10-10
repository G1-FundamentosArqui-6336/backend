package org.upc.cobox.maintenance.domain.services;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.commands.*;

import java.util.Optional;

public interface MaintenanceOrderCommandService {
    Optional<MaintenanceOrder> handle(CreateMaintenanceOrderCommand command);
    Long handle(CompleteMaintenanceOrderCommand command);
    Long handle(CancelMaintenanceOrderCommand command);
    Long handle(ReceivePartsCommand command);
    Long handle(RegisterJobCommand command);
    Long handle(RegisterCostCommand command);
    Long handle(RequestPartsCommand command);
    Long handle(ScheduleMaintenanceOrderCommand command);
    Long handle(StartMaintenanceOrderCommand command);
}
