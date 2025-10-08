package org.upc.cobox.fleet.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.upc.cobox.fleet.domain.model.aggregates.Fleet;

import java.time.LocalDateTime;

@Entity
@Table(name = "route_assignments")
@Getter
@NoArgsConstructor
public class RouteAssignment {

    public enum Status { ASSIGNED, STARTED, COMPLETED, CANCELLED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private Long routeId;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Status status = Status.ASSIGNED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fleet_id", nullable = false)
    private Fleet fleet;

    private LocalDateTime assignedAt;
    private LocalDateTime plannedStart;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    public RouteAssignment(Long routeId, LocalDateTime plannedStart, Fleet fleet) {
        this.routeId = routeId;
        this.plannedStart = plannedStart;
        this.assignedAt = LocalDateTime.now();
        this.fleet = fleet;
    }

    public void start() {
        if (status != Status.ASSIGNED) throw new IllegalStateException("La asignación no puede iniciarse");
        this.status = Status.STARTED;
        this.startedAt = LocalDateTime.now();
    }

    public void complete() {
        if (status != Status.STARTED) throw new IllegalStateException("La asignación no puede completarse");
        this.status = Status.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void cancel() { this.status = Status.CANCELLED; }
}
