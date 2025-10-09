package org.upc.cobox.delivery.domain.model.aggregates;



import org.upc.cobox.Incident.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.domain.model.commands.CreateOrderCommand;
import org.upc.cobox.delivery.domain.model.commands.UpdateOrderStatusCommand;
import org.upc.cobox.delivery.domain.model.events.OrderDeliveredEvent;
import org.upc.cobox.delivery.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


import java.util.Date;
import java.util.Objects;

@Entity
@Getter
public class Order extends AuditableAbstractAggregateRoot<Order> {


    @Embedded
    private ClientId clientId;

    @Embedded
    private Address address;

    @Embedded
    private Reference reference;

    @Embedded
    private ScheduledAt scheduledAt;

    @Embedded
    private DeliveredAt deliveredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeliveryStatus status;

    @Embedded
    private Notes notes;

    @Embedded
    private WeightKg totalWeight;

    // Evidencia (cuando se valida la entrega)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "evidence_id")
    private Evidence evidence;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "fleet_id"))
    private FleetId fleetId;

    protected Order() { /* for JPA */ }

    public Order(CreateOrderCommand command) {
        this.clientId    = new ClientId(command.clientId());
        this.address     = new Address(command.addressLine(), command.city(), command.country(), command.postalCode());
        this.reference   = new Reference(command.reference());
        this.scheduledAt = new ScheduledAt(command.scheduledAt());
        this.status      = DeliveryStatus.SCHEDULED;
        this.notes       = new Notes(command.notes());
        this.totalWeight = new WeightKg(command.totalWeight());
    }

    // === Reglas de dominio ===

    /**
     * actualizarEstado(String) : bool
     */
    public boolean updateStatus(UpdateOrderStatusCommand command) {
        Objects.requireNonNull(command);
        DeliveryStatus newStatus = command.newStatus();

        if (this.status == DeliveryStatus.CANCELLED || this.status == DeliveryStatus.DELIVERED) {
            // Estados terminales; no permitir cambios.
            return false;
        }
        // No permitir saltos inválidos (simple ejemplo)
        if (!this.status.canTransitionTo(newStatus)) return false;

        this.status = newStatus;
        return true;
    }

    /**
     * validarEntrega(Evidencia) : bool
     * - Adjunta evidencia, fija deliveredAt, cambia estado y emite evento de dominio
     */
    public boolean validateDelivery(Evidence evidence) {
        if (this.status == DeliveryStatus.DELIVERED) return false;
        if (evidence == null || !evidence.isValid()) return false;

        this.evidence = evidence;
        this.deliveredAt = new DeliveredAt(new Date());
        this.status = DeliveryStatus.DELIVERED;

        // Domain event  notificaciones/actualizar proyecciones)
        registerEvent(new OrderDeliveredEvent(this.getId(), this.clientId.getClientId(), this.deliveredAt.getDeliveredAt()));
        return true;
    }

    /** Asignar vehículo al pedido por identidad (AR externo). */
    public boolean assignVehicle(FleetId fleetId) {
        if (this.status == DeliveryStatus.CANCELLED || this.status == DeliveryStatus.DELIVERED) return false;
        if (this.fleetId != null && this.fleetId.equals(fleetId)) return true; // ya asignado
        this.fleetId = fleetId;
        return true;
    }

    public boolean unassignVehicle() {
        if (this.status == DeliveryStatus.DELIVERED) return false;
        this.fleetId = null;
        return true;
    }



}
