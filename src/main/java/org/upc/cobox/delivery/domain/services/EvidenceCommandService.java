package org.upc.cobox.delivery.domain.services;

import org.upc.cobox.delivery.domain.model.commands.CreateEvidenceCommand;

public interface EvidenceCommandService {
    Long handle(CreateEvidenceCommand command);
}
