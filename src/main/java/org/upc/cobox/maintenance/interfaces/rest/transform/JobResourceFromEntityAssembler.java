package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.entities.Job;
import org.upc.cobox.maintenance.interfaces.rest.resources.JobResource;

public class JobResourceFromEntityAssembler {
    public static JobResource toResource(Job j) {
        return new JobResource(
                j.getId(),
                j.getDescription(),
                j.getEstimatedDuration(),
                j.isCompleted(),
                j.getTechnicianId(),
                j.getPartNumber()
        );
    }
}
