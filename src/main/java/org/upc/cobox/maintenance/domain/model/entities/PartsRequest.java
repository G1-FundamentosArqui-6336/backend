package org.upc.cobox.maintenance.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.maintenance.domain.model.valueobjects.PartsRequestStatus;
import org.upc.cobox.maintenance.domain.model.valueobjects.Quantity;
import org.upc.cobox.shared.domain.model.entities.AuditableModel;

@Entity
@Getter
public class PartsRequest extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partNumber;

    private String description;

    @Embedded
    private Quantity quantity;

    @Enumerated(EnumType.STRING)
    private PartsRequestStatus status; // REQUESTED, IN_TRANSIT, RECEIVED, UNAVAILABLE

    @Column(name = "supplier_id")
    private Long supplierId;

    protected PartsRequest() {
        // Required by JPA
    }

    public PartsRequest(String partNumber, String description, Quantity quantity) {
        this.partNumber = partNumber;
        this.description = description;
        this.quantity = quantity;
        this.status = PartsRequestStatus.REQUESTED;
    }

    public void assignSupplier(Long supplierId) {
        this.supplierId = supplierId;
    }

    public void markAsInTransit() {
        if (!PartsRequestStatus.REQUESTED.equals(this.status)) {
            throw new IllegalStateException("Can only mark REQUESTED parts as in transit");
        }
        this.status = PartsRequestStatus.IN_TRANSIT;
    }

    public void markAsReceived() {
        if (!PartsRequestStatus.IN_TRANSIT.equals(this.status) && !PartsRequestStatus.REQUESTED.equals(this.status)) {
            throw new IllegalStateException("Invalid status transition");
        }
        this.status = PartsRequestStatus.RECEIVED;
    }

    public void markAsUnavailable() {
        this.status = PartsRequestStatus.UNAVAILABLE;
    }

    public boolean isReceived() {
        return PartsRequestStatus.RECEIVED.equals(this.status);
    }
}