package org.wadajo.turismomadrid.application.client;

import jakarta.ws.rs.GET;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientosTuristicosResponseDto;

@RegisterRestClient(configKey = "turismo-madrid-api")
public interface AlojamientosClient {

    @GET
    AlojamientosTuristicosResponseDto getResponseRaw();
}
