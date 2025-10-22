package org.upc.cobox.delivery.domain.exceptions;

public class EvidenceNotFoundException extends RuntimeException {
    public EvidenceNotFoundException(Long evidenceId) {
        super("Evidence with ID: " + evidenceId + " not found.");
    }
}