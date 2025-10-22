package org.upc.cobox.delivery.application.internal.queryservices;


import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.domain.model.queries.GetEvidenceByIdQuery;
import org.upc.cobox.delivery.domain.services.EvidenceQueryService;
import org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories.EvidenceRepository;

import java.util.Optional;

@Service
public class EvidenceQueryServiceImpl implements EvidenceQueryService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceQueryServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public Optional<Evidence> handle(GetEvidenceByIdQuery query) {
        return evidenceRepository.findById(query.evidenceId());
    }


}
