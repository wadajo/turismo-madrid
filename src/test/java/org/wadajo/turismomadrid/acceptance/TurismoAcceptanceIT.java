package org.wadajo.turismomadrid.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.wadajo.turismomadrid.WireMockExtensions;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.wadajo.turismomadrid.util.TestConstants.ALOJAMIENTOS_QUERY_JSON_FILE;

@QuarkusTest
@QuarkusTestResource(WireMockExtensions.class)
@Disabled
class TurismoAcceptanceIT {

    @Inject
    WireMockServer wireMockServer;

    @TestHTTPResource
    private String LOCALHOST_8080_GRAPHQL = "http://localhost:%s/graphql";

    @Test
    void debeDevolverTodosLosAlojamientosTuristicosAlPedirLaQuery() throws IOException {
        JsonNode alojamientosQueryJson = new JsonMapper().readTree(new File(ALOJAMIENTOS_QUERY_JSON_FILE));

        given()
            .body(alojamientosQueryJson)
            .contentType(ContentType.JSON)
        .when()
            .post(String.format(LOCALHOST_8080_GRAPHQL,wireMockServer.port()))
            .prettyPeek()
        .then()
            .assertThat()
            .body("data.alojamientosTuristicos[0].via_nombre", Matchers.equalTo("del Buen Suceso"))
            .and()
            .body("data.alojamientosTuristicos[1].via_nombre",Matchers.equalTo("de las Seguidillas"))
            .statusCode(200);

    }

}
