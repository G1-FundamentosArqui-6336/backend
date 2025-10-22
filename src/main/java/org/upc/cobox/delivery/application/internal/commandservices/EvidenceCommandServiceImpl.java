package org.upc.cobox.delivery.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.domain.model.commands.CreateEvidenceCommand;
import org.upc.cobox.delivery.domain.services.EvidenceCommandService;
import org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories.EvidenceRepository;


@Service
public class EvidenceCommandServiceImpl implements EvidenceCommandService {

    private final EvidenceRepository evidenceRepository;
    public EvidenceCommandServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public Long handle(CreateEvidenceCommand command) {
        var evidence = new Evidence(command);
        try {
            evidenceRepository.save(evidence);
            return evidence.getId();
        }catch(Exception e){
            throw new IllegalArgumentException(String.format("Error creating the evidence %s", e.getMessage()));
        }
    }

}
