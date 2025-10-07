package org.wadajo.turismomadrid.application.resource;

import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;
import org.wadajo.turismomadrid.domain.service.AlojamientosService;

import java.util.List;

public class AlojamientosResource {

    AlojamientosService service;

    public  AlojamientosResource(AlojamientosService service) {
        this.service = service;
    }

    List<AlojamientoTuristico> alojamientosTuristicos() {
        return service.getAlojamientosTotales();
    }
}
