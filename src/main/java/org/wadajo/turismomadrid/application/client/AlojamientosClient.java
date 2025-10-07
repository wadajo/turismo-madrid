package org.wadajo.turismomadrid.application.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientosTuristicosResponseDto;

@RegisterRestClient(configKey = "turismo-madrid-api")
public interface AlojamientosClient {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    AlojamientosTuristicosResponseDto getResponseWrapper();

}
