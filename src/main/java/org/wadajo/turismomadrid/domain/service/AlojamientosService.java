package org.wadajo.turismomadrid.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.wadajo.turismomadrid.application.client.AlojamientosClient;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientoTuristicoRaw;
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

    public List<AlojamientoTuristico> getAlojamientosTotales() {
        var responseRaw = client.getResponseRaw();
        if (Objects.nonNull(responseRaw.data())) {
            var listaRaw = responseRaw.data();
            listaRaw.sort(Comparator.comparing(AlojamientoTuristicoRaw::alojamiento_tipo).thenComparing(AlojamientoTuristicoRaw::cdpostal));
            return convertFromRaw(listaRaw);
        } else {
            return Collections.emptyList();
        }
    }


}
