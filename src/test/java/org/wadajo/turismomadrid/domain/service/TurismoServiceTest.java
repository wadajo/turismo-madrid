package org.wadajo.turismomadrid.domain.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wadajo.turismomadrid.application.repository.AlojamientoRepository;
import org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento;
import org.wadajo.turismomadrid.domain.exception.TipoNoValidoException;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@QuarkusTest
class TurismoServiceTest {

    @Inject
    TurismoService turismoService;

    @InjectMock
    AlojamientosService alojamientosService;

    @InjectMock
    AlojamientoRepository alojamientoRepository;

    @BeforeEach
    void setUp() {
        List<AlojamientoTuristico> lista = List.of(
            new AlojamientoTuristico("Calle", "Mayor", "45", "A", "", "1", "B", "HST-45", "2 estrellas", "A",
                "Hostal del Sol", "28801", "Alcalá de Henares", TipoAlojamiento.HOSTAL),
            new AlojamientoTuristico("Plaza", "de la Lealtad", "5", "", "", "PB", "", "HT-162", "5 estrellas", "",
                "Hotel Ritz Madrid", "28014", "Madrid", TipoAlojamiento.HOTEL),
            new AlojamientoTuristico("Calle", "Real", "23", "", "", "PB", "", "CR-08", "3 llaves", "",
                "Casa Rural El Mirador", "28492", "Mataelpino", TipoAlojamiento.CASA_RURAL),
            new AlojamientoTuristico("Carretera", "M-608", "27", "", "", "", "", "CMP-100", "2a categoría", "",
                "Camping La Fresneda", "28720", "Bustarviejo", TipoAlojamiento.CAMPING),
            new AlojamientoTuristico("Calle", "Gran Vía", "28", "B", "", "3", "C", "AT-15", "4 llaves", "B",
                "Apartamentos Madrid Centro", "28013", "Madrid", TipoAlojamiento.APART_TURISTICO),
            new AlojamientoTuristico("Calle", "Cervantes", "8", "", "", "2", "A", "PNS-10", "1 estrella", "",
                "Pensión Cervantes", "28014", "Madrid", TipoAlojamiento.PENSION),
            new AlojamientoTuristico("Plaza", "Mayor", "12", "", "", "PB", "", "HST-18", "3 estrellas", "",
                "Hostería Real", "28730", "Buitrago del Lozoya", TipoAlojamiento.HOSTERIAS),
            new AlojamientoTuristico("Calle", "Princesa", "40", "C", "", "1", "", "HA-45", "4 estrellas", "A",
                "Apart-Hotel Gran Madrid", "28008", "Madrid", TipoAlojamiento.HOTEL_APART),
            new AlojamientoTuristico("Calle", "del Olmo", "5", "", "", "PB", "", "HR-20", "3 estrellas", "",
                "Hotel Rural La Encina", "28739", "Gargantilla del Lozoya", TipoAlojamiento.HOTEL_RURAL),
            new AlojamientoTuristico("Calle", "Toledo", "67", "A", "", "1", "D", "CH-12", "2 estrellas", "B",
                "Casa Huéspedes Victoria", "28005", "Madrid", TipoAlojamiento.CASA_HUESPEDES),
            new AlojamientoTuristico("Calle", "Alta", "3", "", "", "1", "", "AR-06", "2 llaves", "",
                "Apartamento Rural Valle", "28743", "Garganta de los Montes", TipoAlojamiento.APARTAMENTO_RURAL),
            new AlojamientoTuristico("Calle", "Huertas", "21", "B", "", "2", "C", "VT-01", "3 llaves", "A",
                "Airbnb Centro Histórico", "28014", "Madrid", TipoAlojamiento.VIVIENDAS_TURISTICAS)
        );
        when(alojamientosService.getAlojamientosTotales()).thenReturn(lista);
    }

    @Test
    void debeObtenerTodosLosAlojamientos() {
        var alojamientos = turismoService.getAlojamientosTuristicosEnRemoto();
        assertThat(alojamientos).hasSize(12);
    }

    @Test
    void debeFiltrarAlojamientosSegunElTipoSolicitado() {
        var filtrados = turismoService.getAlojamientosByType(TipoAlojamiento.HOSTERIAS.toString());

        assertThat(filtrados)
            .hasSize(1)
            .first()
            .hasFieldOrPropertyWithValue("via_nombre", "Mayor");
    }

    @Test
    void debeArrojarExcepcionSiElTipoNoEsValido() {
        assertThatThrownBy(() -> {
            turismoService.getAlojamientosByType("Horreo");
        }).isInstanceOf(TipoNoValidoException.class)
          .hasMessageContaining("Tipo de alojamiento turístico no válido: Horreo");
    }

    @Test
    void debeInvocarElRepositorioParaBorrar(){
        when(alojamientoRepository.deleteAll()).thenReturn(1L);

        turismoService.borrarTodo();

        verify(alojamientoRepository, times(1)).deleteAll();
    }

    @Test
    void debeInvocarElRepositorioParaGuardar(){
        var resultado = turismoService.guardarTodosLosAlojamientosRemotosEnDb();

        assertThat(resultado).isEqualTo("Han sido guardados en DB: 12 alojamientos.");
    }

}