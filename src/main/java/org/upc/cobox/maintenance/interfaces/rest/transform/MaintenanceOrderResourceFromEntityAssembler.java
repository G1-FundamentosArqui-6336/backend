package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.interfaces.rest.resources.*;
import java.util.List;

public class MaintenanceOrderResourceFromEntityAssembler {

    public static MaintenanceOrderResource toResource(MaintenanceOrder e) {
        var tl = e.getScheduledTimelapse();
        var jobs = e.getJobs() == null ? List.<JobResource>of()
                : e.getJobs().stream().map(JobResourceFromEntityAssembler::toResource).toList();

        var prs = e.getPartsRequests() == null ? List.<PartsRequestResource>of()
                : e.getPartsRequests().stream().map(PartsRequestResourceFromEntityAssembler::toResource).toList();

        return new MaintenanceOrderResource(
                e.getId(),
                e.getVehicleId().vehicleId() != null ? e.getVehicleId().vehicleId() : null,
                e.getMaintenanceType() != null ? e.getMaintenanceType().name() : null,
                e.getPriority() != null ? e.getPriority().name() : null,
                e.getStatus() != null ? e.getStatus().name() : null,
                e.getReason() != null ? e.getReason().name() : null,
                e.getOpeningOdometer() != null ? e.getOpeningOdometer().km() : null,
                e.getClosingOdometer() != null ? e.getClosingOdometer().km() : null,
                tl != null ? tl.getStartTime() : null,
                tl != null ? tl.getEndTime() : null,
                e.getTotalCost() != null ? e.getTotalCost().getAmount() : null,
                e.getTotalCost() != null ? e.getTotalCost().getCurrency() : null,
                e.getTechnicianId(),
                jobs,
                prs
        );
    }
}
