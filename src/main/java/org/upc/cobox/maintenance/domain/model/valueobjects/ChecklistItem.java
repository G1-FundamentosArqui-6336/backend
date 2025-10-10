package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ChecklistItem {
    private String title;
    private String status; // PENDING, OK, NOT_APPLICABLE

    protected ChecklistItem() {}

    public ChecklistItem(String title) {
        this.title = title;
        this.status = "PENDING";
    }

    public void complete(String status) {
        if (!"OK".equals(status) && !"NOT_APPLICABLE".equals(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

    public boolean isCompleted() {
        return !"PENDING".equals(this.status);
    }
}