package org.upc.cobox.maintenance.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceOrderByIdQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceOrderHistoryQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetOpenMaintenanceOrdersByVehicleIdQuery;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceOrdersByStatusQuery;
import org.upc.cobox.maintenance.domain.model.queries.HasMaintenanceOpenOrderForVehicleIdQuery;
import org.upc.cobox.maintenance.domain.model.valueobjects.MaintenanceOrderStatus;
import org.upc.cobox.maintenance.domain.model.valueobjects.VehicleId;
import org.upc.cobox.maintenance.domain.services.MaintenanceOrderQueryService;
import org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MaintenanceOrderQueryServiceImpl implements MaintenanceOrderQueryService {

    private final MaintenanceOrderRepository orderRepository;

    public MaintenanceOrderQueryServiceImpl(MaintenanceOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<MaintenanceOrder> handle(GetMaintenanceOrderByIdQuery query) {
        return orderRepository.findById(query.maintenanceOrderId());
    }

    @Override
    public List<MaintenanceOrder> handle(GetMaintenanceOrderHistoryQuery query) {
        return orderRepository.findHistoryByVehicleId(new VehicleId(query.vehicleId()));
    }

    @Override
    public List<MaintenanceOrder> handle(GetOpenMaintenanceOrdersByVehicleIdQuery query) {
        return orderRepository.findOpenOrdersByVehicleId(new VehicleId(query.vehicleId()));
    }

    @Override
    public List<MaintenanceOrder> handle(GetMaintenanceOrdersByStatusQuery query) {
        return orderRepository.findByStatus(MaintenanceOrderStatus.valueOf(query.status()));
    }

    @Override
    public boolean handle(HasMaintenanceOpenOrderForVehicleIdQuery query) {
        return orderRepository.existsOpenOrderByVehicleId(new VehicleId(query.vehicleId()));
    }
}
