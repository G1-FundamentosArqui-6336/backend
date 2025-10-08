package org.upc.cobox.fleet.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;
import org.upc.cobox.fleet.domain.model.valueobjects.Estado;
import org.upc.cobox.fleet.domain.model.valueobjects.Placa;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fleets", uniqueConstraints = @UniqueConstraint(columnNames = "placa"))
@Getter
@NoArgsConstructor
public class Fleet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "placa", nullable = false, length = 10))
    })
    private Placa placa;

    @NotBlank @Column(nullable = false)
    private String marca;

    @NotBlank @Column(nullable = false)
    private String modelo;

    @Min(1) @Column(nullable = false)
    private Integer capacidadKg;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Estado estado = Estado.DISPONIBLE;

    @OneToMany(mappedBy = "fleet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RouteAssignment> assignments = new HashSet<>();

    public Fleet(Placa placa, String marca, String modelo, Integer capacidadKg) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadKg = capacidadKg;
        this.estado = Estado.DISPONIBLE;
    }

    // Reglas del dominio
    public void actualizarDatos(String marca, String modelo, Integer capacidadKg) {
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadKg = capacidadKg;
    }

    public void cambiarEstado(Estado nuevo) { this.estado = nuevo; }

    public RouteAssignment assignRoute(Long routeId, java.time.LocalDateTime plannedStart) {
        if (this.estado == Estado.AVERIADO)
            throw new IllegalStateException("No se puede asignar ruta: unidad averiada");
        var a = new RouteAssignment(routeId, plannedStart, this);
        assignments.add(a);
        this.estado = Estado.OCUPADO;
        return a;
    }

    public void markRouteStarted(Long assignmentId) {
        var a = assignments.stream()
                .filter(x -> x.getId().equals(assignmentId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Asignación no existe"));
        a.start();
        this.estado = Estado.EN_RUTA;
    }

    public void markRouteCompleted(Long assignmentId) {
        var a = assignments.stream()
                .filter(x -> x.getId().equals(assignmentId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Asignación no existe"));
        a.complete();
        this.estado = Estado.DISPONIBLE;
    }
}
