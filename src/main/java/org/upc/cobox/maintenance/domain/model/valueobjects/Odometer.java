package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Odometer {
    @Column(name = "km")
    private int km;

    protected Odometer() {
    }

    public Odometer(int km) {
        if (km < 0) throw new IllegalArgumentException("km>=0");
        this.km = km;
    }

    public int km() {
        return km;
    }
}