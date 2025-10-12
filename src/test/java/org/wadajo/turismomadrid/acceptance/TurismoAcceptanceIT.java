package org.wadajo.turismomadrid.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.wadajo.turismomadrid.MyWiremockResource;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.wadajo.turismomadrid.util.TestConstants.ALOJAMIENTOS_QUERY_JSON_FILE;

@QuarkusIntegrationTest
@QuarkusTestResource(MyWiremockResource.class)
class TurismoAcceptanceIT {

    @Test
    void debeDevolverTodosLosAlojamientosTuristicosAlPedirLaQuery() throws IOException {
        JsonNode alojamientosQueryJson = new JsonMapper().readTree(new File(ALOJAMIENTOS_QUERY_JSON_FILE));

        given()
            .body(alojamientosQueryJson)
            .contentType(ContentType.JSON)
        .when()
            .post("/graphql")
            .prettyPeek()
        .then()
            .assertThat()
            .body("data.alojamientosTuristicos[0].via_nombre", Matchers.equalTo("del Buen Suceso"))
            .and()
            .body("data.alojamientosTuristicos[1].via_nombre",Matchers.equalTo("de las Seguidillas"))
            .statusCode(200);
    }

}
