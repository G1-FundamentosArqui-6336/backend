package org.upc.cobox.delivery.domain.model.aggregates;

import jakarta.persistence.Entity;
import lombok.Getter;
import org.upc.cobox.delivery.domain.model.commands.CreateEvidenceCommand;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


@Getter
@Entity
public class Evidence extends AuditableAbstractAggregateRoot<Evidence> {

    private String receiverName;

    private String photoUrl; // o ruta al blob


    protected Evidence() {}

    public Evidence(CreateEvidenceCommand command) {
        this.receiverName = command.receiverName();
        this.photoUrl = command.photoUrl();
    }


}
