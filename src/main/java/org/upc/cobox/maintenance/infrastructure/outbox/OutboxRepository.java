package org.upc.cobox.maintenance.infrastructure.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxMessage, Long> {
    List<OutboxMessage> findTop50ByStatusOrderByOccurredOnAsc(String status);
}
