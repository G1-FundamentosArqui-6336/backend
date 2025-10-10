package org.upc.cobox.maintenance.domain.services;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface MaintenanceOrderQueryService {
    Optional<MaintenanceOrder> handle(GetMaintenanceOrderByIdQuery query);
    List<MaintenanceOrder> handle(GetMaintenanceOrderHistoryQuery query);
    List<MaintenanceOrder> handle(GetOpenMaintenanceOrdersByVehicleIdQuery query);
    List<MaintenanceOrder> handle(GetMaintenanceOrdersByStatusQuery query);
    boolean handle(HasMaintenanceOpenOrderForVehicleIdQuery query);
}
