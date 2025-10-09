package org.upc.cobox.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpdateFleetResource(
        @NotBlank String marca, @NotBlank String modelo, BigDecimal capacidadKg) {}
