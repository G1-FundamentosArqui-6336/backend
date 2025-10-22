package org.upc.cobox.delivery.infraestructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.delivery.domain.model.aggregates.Evidence;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
}
