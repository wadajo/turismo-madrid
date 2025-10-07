package org.wadajo.turismomadrid.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.wadajo.turismomadrid.application.client.AlojamientosClient;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientoTuristicoRaw;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientosTuristicosResponseDto;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.wadajo.turismomadrid.infrastructure.util.Utils.convertFromRaw;

@ApplicationScoped
public class AlojamientosService {

    private final AlojamientosClient client;

    AlojamientosService(@RestClient AlojamientosClient client) {
        this.client = client;
    }

    public List<AlojamientoTuristico> getAlojamientosTotales() throws JsonProcessingException {
        var responseRawString = client.getResponseString();
        return getOrderedAlojamientosTuristicosFromRawString(responseRawString);
    }

    private static List<AlojamientoTuristico> getOrderedAlojamientosTuristicosFromRawString(String responseString) throws JsonProcessingException {
        var responseRaw = new JsonMapper().readValue(responseString, AlojamientosTuristicosResponseDto.class);
        if (Objects.nonNull(responseRaw.data())) {
            var listaRaw = responseRaw.data();
            listaRaw.sort(Comparator.comparing(AlojamientoTuristicoRaw::alojamiento_tipo)
                .thenComparing(AlojamientoTuristicoRaw::cdpostal));
            return convertFromRaw(listaRaw);
        } else {
            return Collections.emptyList();
        }
    }


}
