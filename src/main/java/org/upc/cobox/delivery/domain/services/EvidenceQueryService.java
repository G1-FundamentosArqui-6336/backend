package org.upc.cobox.delivery.domain.services;

import org.upc.cobox.delivery.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.domain.model.queries.GetEvidenceByIdQuery;

import java.util.Optional;

public interface EvidenceQueryService {
    Optional<Evidence> handle(GetEvidenceByIdQuery query);
}
