package org.upc.cobox.maintenance.domain.model.valueobjects;

public enum Reason {
    TIME, MILEAGE, MIXED, ALERT;

    public static Reason fromCriteria(String c) {
        return valueOf(c.toUpperCase());
    }
}