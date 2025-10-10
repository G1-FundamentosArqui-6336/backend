package org.upc.cobox.fleet.interfaces.rest.resources;

import java.math.BigDecimal;

public record FleetResource(Long id, String placa, String marca, String modelo, BigDecimal capacidadKg, String estado) {}
