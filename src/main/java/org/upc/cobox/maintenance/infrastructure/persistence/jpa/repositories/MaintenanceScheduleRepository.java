package org.upc.cobox.maintenance.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.domain.model.valueobjects.VehicleId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {

    /**
     * Obtiene todos los schedules activos.
     */
    @Query("SELECT ms FROM MaintenanceSchedule ms WHERE ms.status = 'ACTIVE'")
    List<MaintenanceSchedule> findActiveSchedules();

    /**
     * Obtiene los schedules que deben evaluarse pronto (basado en la fecha próxima de evaluación).
     */
    @Query("SELECT ms FROM MaintenanceSchedule ms " +
            "WHERE ms.status = 'ACTIVE' AND ms.nextEvaluationAt <= :limitDate")
    List<MaintenanceSchedule> findDueSoon(@Param("limitDate") LocalDateTime limitDate);

    /**
     * Obtiene el schedule asociado a un vehículo.
     */
    @Query("SELECT ms FROM MaintenanceSchedule ms WHERE ms.vehicleId = :vehicleId")
    Optional<MaintenanceSchedule> findByVehicleId(@Param("vehicleId") VehicleId vehicleId);

    /**
     * Verifica si un vehículo ya tiene un schedule activo.
     */
    @Query("SELECT COUNT(ms) > 0 FROM MaintenanceSchedule ms " +
            "WHERE ms.vehicleId = :vehicleId AND ms.status = 'ACTIVE'")
    boolean existsActiveByVehicleId(@Param("vehicleId") VehicleId vehicleId);
}
