package org.upc.cobox.delivery.domain.model.valueobjects;

public enum DeliveryStatus {
    SCHEDULED,
    IN_TRANSIT,
    DELAYED,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(DeliveryStatus next) {
        switch (this) {
            case SCHEDULED: return next == IN_TRANSIT || next == CANCELLED || next == DELAYED;
            case IN_TRANSIT: return next == DELIVERED || next == DELAYED || next == CANCELLED;
            case DELAYED: return next == IN_TRANSIT || next == CANCELLED || next == DELIVERED;
            default: return false; // DELIVERED / CANCELLED are terminal
        }
    }
}