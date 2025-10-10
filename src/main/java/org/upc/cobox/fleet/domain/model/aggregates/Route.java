package org.upc.cobox.fleet.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.fleet.domain.model.valueobjects.OrderId;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;


@Entity
@Getter
public class Route extends AuditableAbstractAggregateRoot<Route> {



    @Embedded
    private OrderId OrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;


    protected Route() {}

}