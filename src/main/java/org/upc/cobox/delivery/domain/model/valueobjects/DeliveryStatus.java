package org.upc.cobox.delivery.domain.model.valueobjects;

public enum DeliveryStatus {
    /** Pedido creado, pero sin asignación a ruta aún. */
    SCHEDULED,
    /** Pedido ha sido asignado a una ruta, flota y conductor. */
    ASSIGNED,
    /** La entrega ha comenzado (vehículo en movimiento). */
    IN_TRANSIT,
    /** La entrega se ha retrasado. */
    DELAYED,
    /** La entrega se ha completado exitosamente. */
    DELIVERED,
    /** El pedido ha sido anulado. */
    CANCELLED;

    public boolean canTransitionTo(DeliveryStatus next) {
        switch (this) {
            case SCHEDULED:
                // Puede pasar a asignado, en tránsito (si se planifica instantáneamente), cancelado o retrasado.
                return next == ASSIGNED || next == IN_TRANSIT || next == CANCELLED || next == DELAYED;
            case ASSIGNED:
                // Después de asignado, debe pasar a en tránsito o ser cancelado/retrasado antes de iniciar.
                return next == IN_TRANSIT || next == CANCELLED || next == DELAYED;
            case IN_TRANSIT:
                // Puede pasar a entregado, retrasado o cancelado.
                return next == DELIVERED || next == DELAYED || next == CANCELLED;
            case DELAYED:
                // Desde retrasado puede reanudar la ruta, ser cancelado o finalmente ser entregado.
                return next == IN_TRANSIT || next == CANCELLED || next == DELIVERED;
            default:
                // DELIVERED y CANCELLED son estados terminales (no pueden cambiar).
                return false;
        }
    }
}