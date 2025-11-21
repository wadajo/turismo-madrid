package org.wadajo.turismomadrid.application.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wadajo.turismomadrid.domain.document.AlojamientoDocument;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@QuarkusTest
class AlojamientoRepositoryTest {

    @Inject
    AlojamientoRepository alojamientoRepository;

    @BeforeEach
    void setUp() {
        alojamientoRepository.deleteAll();
    }

    @Test
    void debeHaberElementosTrasGuardarlos() {
        var alojamientos = Instancio.stream(AlojamientoDocument.class)
                                .limit(12)
                                .toList();

        alojamientoRepository.persist(alojamientos);
        var count = alojamientoRepository.count();

        assertThat(count).isEqualTo(12L);
    }

}