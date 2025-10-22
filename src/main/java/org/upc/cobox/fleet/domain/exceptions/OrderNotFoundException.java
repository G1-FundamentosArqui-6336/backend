package org.upc.cobox.fleet.domain.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("External order with ID " + orderId + " not found or is invalid.");
    }
}