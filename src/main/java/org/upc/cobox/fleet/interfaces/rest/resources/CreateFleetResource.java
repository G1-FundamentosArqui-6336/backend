package org.upc.cobox.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateFleetResource(
        @NotBlank String placa, @NotBlank String marca, @NotBlank String modelo, @Min(1) Integer capacidadKg) {}
