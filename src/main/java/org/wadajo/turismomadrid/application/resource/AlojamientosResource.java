package org.wadajo.turismomadrid.application.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;
import org.wadajo.turismomadrid.domain.service.AlojamientosService;

import java.util.List;

@GraphQLApi
public class AlojamientosResource {

    AlojamientosService service;

    public AlojamientosResource(AlojamientosService service) {
        this.service = service;
    }

    @Query
    @Description("Devuelve todos los alojamientos turisticos de la Comunidad de Madrid")
    public List<AlojamientoTuristico> alojamientosTuristicos() throws JsonProcessingException {
        return service.getAlojamientosTotales();
    }
}
