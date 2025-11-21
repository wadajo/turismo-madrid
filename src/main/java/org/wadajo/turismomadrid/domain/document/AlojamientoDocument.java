package org.wadajo.turismomadrid.domain.document;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

@MongoEntity(collection = "alojamientos")
public class AlojamientoDocument extends PanacheMongoEntity {

    public @Nullable String via_tipo, via_nombre, numero, portal, bloque, planta, puerta, signatura, categoria, escalera, denominacion, codpostal, localidad;

    /**
     * Tipo de alojamiento turístico, siempre incluido en BBDD.
     * Ejemplos: HOTEL, HOSTAL, VIVIENDAS DE USO TURÍSTICO, etc.
     */
    @SuppressWarnings("NullAway")
    public String alojamiento_tipo;

    /**
     * Campo generado por BBDD, no se debe modificar.
     */
    @SuppressWarnings("NullAway")
    public LocalDateTime timestamp;

}
