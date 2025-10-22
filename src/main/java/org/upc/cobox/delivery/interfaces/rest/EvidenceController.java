package org.upc.cobox.delivery.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.cobox.delivery.domain.model.queries.GetEvidenceByIdQuery;
import org.upc.cobox.delivery.domain.services.EvidenceCommandService;
import org.upc.cobox.delivery.domain.services.EvidenceQueryService;
import org.upc.cobox.delivery.interfaces.rest.resources.CreateEvidenceResource;
import org.upc.cobox.delivery.interfaces.rest.resources.EvidenceResource;
import org.upc.cobox.delivery.interfaces.rest.transform.CreateEvidenceCommandFromResourceAssembler;
import org.upc.cobox.delivery.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/evidences", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Evidences", description = "Evidence Management Endpoints")
public class EvidenceController {
    private final EvidenceCommandService evidenceCommandService;
    private final EvidenceQueryService evidenceQueryService;

    public EvidenceController(
            EvidenceCommandService evidenceCommandService,
            EvidenceQueryService evidenceQueryService) {
        this.evidenceCommandService = evidenceCommandService;
        this.evidenceQueryService = evidenceQueryService;
    }

    /**
     * Crea una nueva evidencia. Corresponde al método handle(CreateEvidenceCommand).
     *
     * @param resource el recurso con los datos para crear la evidencia.
     * @return el recurso de la evidencia creada.
     */
    @Operation(summary = "Create new evidence",
            description = "Creates new evidence for an order (e.g., a photo, signature) and returns the created evidence's details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Evidence created successfully",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EvidenceResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE))
            })
    @PostMapping
    public ResponseEntity<EvidenceResource> createEvidence(@RequestBody CreateEvidenceResource resource) {
        // 1. Convierte el Recurso (DTO) a un Comando
        var createEvidenceCommand = CreateEvidenceCommandFromResourceAssembler.toCommandFromResource(resource);

        // 2. Ejecuta el comando para crear la entidad y obtener su ID
        var evidenceId = evidenceCommandService.handle(createEvidenceCommand);
        if (evidenceId == 0L) {
            return ResponseEntity.badRequest().build();
        }

        // 3. Usa el ID para consultar la entidad recién creada
        var getEvidenceByIdQuery = new GetEvidenceByIdQuery(evidenceId);
        var evidence = evidenceQueryService.handle(getEvidenceByIdQuery);
        if (evidence.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 4. Convierte la Entidad a un Recurso (DTO) para la respuesta
        var evidenceResource = EvidenceResourceFromEntityAssembler.toResourceFromEntity(evidence.get());
        return new ResponseEntity<>(evidenceResource, HttpStatus.CREATED);
    }

    /**
     * Obtiene una evidencia por su ID.
     */
    @Operation(summary = "Get evidence by its ID",
            description = "Retrieves the details of a single evidence item by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidence found",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EvidenceResource.class))),
                    @ApiResponse(responseCode = "404", description = "Evidence not found",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE))
            })
    @GetMapping("/{evidenceId}")
    public ResponseEntity<EvidenceResource> getEvidenceById(@PathVariable Long evidenceId) {
        var getEvidenceByIdQuery = new GetEvidenceByIdQuery(evidenceId);
        var evidence = evidenceQueryService.handle(getEvidenceByIdQuery);
        if (evidence.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var evidenceResource = EvidenceResourceFromEntityAssembler.toResourceFromEntity(evidence.get());
        return ResponseEntity.ok(evidenceResource);
    }
}