package org.upc.cobox.maintenance.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.maintenance.domain.model.valueobjects.ChecklistItem;
import org.upc.cobox.shared.domain.model.entities.AuditableModel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Job extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String partNumber;

    private Integer estimatedDuration; // minutes

    private Integer actualDuration; // minutes

    @ElementCollection
    @CollectionTable(name = "job_checklist_items", joinColumns = @JoinColumn(name = "job_id"))
    private List<ChecklistItem> checklist = new ArrayList<>();

    @Column(name = "technician_id")
    private Long technicianId;

    protected Job() {
        // Required by JPA
    }

    public Job(String description, Integer estimatedDuration) {
        this.description = description;
        this.estimatedDuration = estimatedDuration;
    }

    public void assignTechnician(Long technicianId) {
        this.technicianId = technicianId;
    }

    public void assignPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public void addChecklistItem(ChecklistItem item) {
        this.checklist.add(item);
    }

    public void completeChecklistItem(int index, String status) {
        if (index < 0 || index >= checklist.size()) {
            throw new IllegalArgumentException("Invalid checklist item index");
        }
        checklist.get(index).complete(status);
    }

    public void recordActualDuration(Integer duration) {
        this.actualDuration = duration;
    }

    public boolean isCompleted() {
        return checklist.stream().allMatch(ChecklistItem::isCompleted);
    }
}
