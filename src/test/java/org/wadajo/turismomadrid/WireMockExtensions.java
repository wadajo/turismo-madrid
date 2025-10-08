package org.wadajo.turismomadrid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.wadajo.turismomadrid.util.TestConstants.ALOJAMIENTOS_RAW_STUBBING_FILE;

public class WireMockExtensions implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        try {
            JsonNode alojamientosRaw = new ObjectMapper().readTree(new File(ALOJAMIENTOS_RAW_STUBBING_FILE));

            stubFor(get("/")
                .willReturn(aResponse()
                    .withStatus(200)
                    .withJsonBody(alojamientosRaw)
                ));

            return Map.of("quarkus.rest-client.turismo-madrid-api.url", wireMockServer.baseUrl());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}