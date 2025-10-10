package org.upc.cobox.maintenance.infrastructure.outbox;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "outbox")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OutboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nombre del evento canónico, ej: "maintenance.order.created"
    @Column(nullable = false, length = 100)
    private String type;

    // cuerpo JSON del evento
    @Lob
    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private OffsetDateTime occurredOn;

    // para control de entrega
    @Column(nullable = false, length = 20)
    private String status; // NEW, SENT, FAILED

    @Column
    private Integer attempts;
}
