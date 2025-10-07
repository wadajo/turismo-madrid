package org.wadajo.turismomadrid.application.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "turismo-madrid-api")
public interface AlojamientosClient {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    String getResponseString();

}
