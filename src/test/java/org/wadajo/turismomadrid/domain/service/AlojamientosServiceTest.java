package org.wadajo.turismomadrid.domain.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wadajo.turismomadrid.application.client.AlojamientosClient;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientosTuristicosResponseDto;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.wadajo.turismomadrid.util.TestConstants.ALOJAMIENTOS_RAW_STUBBING_FILE;

@QuarkusTest
class AlojamientosServiceTest {

    @Inject
    AlojamientosService alojamientosService;

    @InjectMock
    @RestClient
    AlojamientosClient alojamientosClient;

    @BeforeEach
    void setUp() throws IOException {
        var alojamientosRaw = new JsonMapper().readValue(
                                    new File(ALOJAMIENTOS_RAW_STUBBING_FILE), AlojamientosTuristicosResponseDto.class);
        when(alojamientosClient.getResponseWrapper()).thenReturn(alojamientosRaw);
    }

    @Test
    void debeObtenerTodosLosAlojamientos() {
        var alojamientos = alojamientosService.getAlojamientosTotales();
        assertThat(alojamientos).hasSize(12);
    }
}