package org.upc.cobox.Incident.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Getter
@Entity
public class Evidence extends AuditableAbstractAggregateRoot<Evidence> {


    private String receiverName;

    private String photoUrl; // o ruta al blob

    private String signatureCode; // hash/OTP/código

    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    protected Evidence() {}

    public Evidence(String receiverName, String photoUrl, String signatureCode, Date takenAt) {
        this.receiverName = receiverName;
        this.photoUrl = photoUrl;
        this.signatureCode = signatureCode;
        this.takenAt = takenAt == null ? new Date() : takenAt;
    }

    public boolean isValid() {
        return receiverName != null && !receiverName.isBlank()
                && (photoUrl != null && !photoUrl.isBlank() || signatureCode != null && !signatureCode.isBlank());
    }
}
