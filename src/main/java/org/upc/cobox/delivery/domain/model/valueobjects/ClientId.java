package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class ClientId {
    @Getter
    private Long clientId;

    protected ClientId() {}
    public ClientId(Long clientId) {
        if (clientId == null || clientId <= 0) throw new IllegalArgumentException("ClientId inválido");
        this.clientId = clientId;
    }
    @Override public boolean equals(Object o){ return o instanceof ClientId c && Objects.equals(clientId, c.clientId);}
    @Override public int hashCode(){ return Objects.hash(clientId); }
}