package org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.valueobjects.MaintenanceOrderStatus;
import org.upc.cobox.maintenance.domain.model.valueobjects.VehicleId;

import java.util.List;

public interface MaintenanceOrderRepository extends JpaRepository<MaintenanceOrder, Long> {
    /**
     * Obtiene todas las órdenes de un vehículo específico, ordenadas por fecha de creación descendente.
     */
    List<MaintenanceOrder> findByVehicleIdOrderByCreatedAtDesc(VehicleId vehicleId);

    /**
     * Obtiene el historial (todas las órdenes completadas o canceladas) de un vehículo.
     */
    @Query("SELECT mo FROM MaintenanceOrder mo " +
            "WHERE mo.vehicleId = :vehicleId " +
            "AND (mo.status = 'COMPLETED' OR mo.status = 'CANCELLED') " +
            "ORDER BY mo.createdAt DESC")
    List<MaintenanceOrder> findHistoryByVehicleId(@Param("vehicleId") VehicleId vehicleId);

    /**
     * Obtiene las órdenes abiertas para un vehículo (por ejemplo, para evitar duplicadas).
     */
    @Query("SELECT mo FROM MaintenanceOrder mo " +
            "WHERE mo.vehicleId = :vehicleId AND mo.status IN ('OPEN', 'SCHEDULED', 'IN_PROGRESS')")
    List<MaintenanceOrder> findOpenOrdersByVehicleId(@Param("vehicleId") VehicleId vehicleId);

    /**
     * Verifica si existe una orden abierta para un vehículo (utilizado por el command handler).
     */
    @Query("SELECT COUNT(mo) > 0 FROM MaintenanceOrder mo " +
            "WHERE mo.vehicleId = :vehicleId AND mo.status IN ('OPEN', 'SCHEDULED', 'IN_PROGRESS')")
    boolean existsOpenOrderByVehicleId(@Param("vehicleId") VehicleId vehicleId);

    /**
     * Obtiene órdenes según su estado (para dashboards o reportes).
     */
    List<MaintenanceOrder> findByStatus(MaintenanceOrderStatus status);
}