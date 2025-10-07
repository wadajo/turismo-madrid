package org.wadajo.turismomadrid.infrastructure.util;

import io.quarkus.logging.Log;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientoTuristicoRaw;
import org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<AlojamientoTuristico> convertFromRaw(List<AlojamientoTuristicoRaw> listaRaw){
        var alojamientosTuristicos=new ArrayList<AlojamientoTuristico>();
        listaRaw.forEach(alojamientoTuristicoRaw -> {
            switch (alojamientoTuristicoRaw.alojamiento_tipo()) {
                case "APARTAMENTO RURAL" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.APARTAMENTO_RURAL
                ));
                case "APART-TURISTICO" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.APART_TURISTICO
                ));
                case "CAMPING" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.CAMPING
                ));
                case "CASA HUESPEDES" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.CASA_HUESPEDES
                ));
                case "CASA RURAL" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.CASA_RURAL
                ));
                case "HOSTAL" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.HOSTAL
                ));
                case "HOSTERIAS" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.HOSTERIAS
                ));
                case "HOTEL" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.HOTEL
                ));
                case "HOTEL-APART." -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.HOTEL_APART
                ));
                case "HOTEL RURAL" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.HOTEL_RURAL
                ));
                case "PENSION" -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.PENSION
                ));
                case "VIVIENDAS DE USO TU " -> alojamientosTuristicos.add(new AlojamientoTuristico(
                    alojamientoTuristicoRaw.via_tipo(),
                    alojamientoTuristicoRaw.via_nombre(),
                    alojamientoTuristicoRaw.numero(),
                    alojamientoTuristicoRaw.portal(),
                    alojamientoTuristicoRaw.bloque(),
                    alojamientoTuristicoRaw.planta(),
                    alojamientoTuristicoRaw.puerta(),
                    alojamientoTuristicoRaw.signatura(),
                    alojamientoTuristicoRaw.categoria(),
                    alojamientoTuristicoRaw.escalera(),
                    alojamientoTuristicoRaw.denominacion(),
                    alojamientoTuristicoRaw.cdpostal(),
                    alojamientoTuristicoRaw.localidad(),
                    TipoAlojamiento.VIVIENDAS_TURISTICAS
                ));
                default -> Log.error("not recognized alojamiento tipo: " + alojamientoTuristicoRaw.alojamiento_tipo());
            }
        });
        return Collections.unmodifiableList(alojamientosTuristicos);
    }

}
