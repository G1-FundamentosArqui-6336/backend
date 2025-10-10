package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class DeliveredAt {
    @Getter private Date deliveredAt;
    protected DeliveredAt() {}
    public DeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
    @Override public boolean equals(Object o){ return o instanceof DeliveredAt d && Objects.equals(deliveredAt, d.deliveredAt);}
    @Override public int hashCode(){ return Objects.hash(deliveredAt); }
}