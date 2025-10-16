package org.wadajo.turismomadrid.application.resource;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;
import org.wadajo.turismomadrid.domain.service.TurismoService;

import java.util.List;

@GraphQLApi
public class AlojamientosResource {

    TurismoService service;

    public AlojamientosResource(TurismoService service) {
        this.service = service;
    }

    @Query
    @Description("Devuelve todos los alojamientos turisticos de la Comunidad de Madrid")
    public List<AlojamientoTuristico> alojamientosTuristicos() {
        return service.getAlojamientosTuristicosEnRemoto();
    }

    @Mutation
    @Description("Actualiza la base de datos con los alojamientos turisticos de la Comunidad de Madrid")
    public String actualizarDB(){
        return service.guardarTodosLosAlojamientosRemotosEnDb();
    }
}
