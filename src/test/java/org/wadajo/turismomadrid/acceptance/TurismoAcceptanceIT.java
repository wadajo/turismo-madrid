package org.wadajo.turismomadrid.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.wadajo.turismomadrid.util.TestConstants.*;

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
            .post(GRAPHQL)
            .prettyPeek()
        .then()
            .assertThat()
            .body("data.alojamientosTuristicos[0].via_nombre", Matchers.equalTo("del Buen Suceso"))
            .and()
            .body("data.alojamientosTuristicos[1].via_nombre",Matchers.equalTo("de las Seguidillas"))
            .statusCode(200);
    }

    @Test
    void debeActualizarDb() throws IOException {
        JsonNode mutationActualizarDbJson = new ObjectMapper().readTree(new File(ALOJAMIENTOS_ACTUALIZAR_FILE));

        given()
            .body(mutationActualizarDbJson)
            .contentType(ContentType.JSON)
        .when()
            .post(GRAPHQL)
            .prettyPeek()
        .then()
            .assertThat()
            .body("data.actualizarDB", Matchers.equalTo(RESULTADO_API_ACTUALIZARDB))
            .statusCode(200);

    }

}
