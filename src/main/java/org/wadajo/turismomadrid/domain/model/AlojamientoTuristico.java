package org.wadajo.turismomadrid.domain.model;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Type;
import org.jspecify.annotations.NullMarked;
import org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento;

@NullMarked
@Type("AlojamientoTuristico")
@Description("Representa un alojamiento turístico en la Comunidad de Madrid")
public record AlojamientoTuristico (
        @Description("Tipo de vía del alojamiento")
        String via_tipo,
        @Description("Nombre de la vía del alojamiento")
        String via_nombre,
        @Description("Número del alojamiento")
        String numero,
        @Description("Portal del alojamiento")
        String portal,
        @Description("Bloque del alojamiento")
        String bloque,
        @Description("Planta del alojamiento")
        String planta,
        @Description("Puerta del alojamiento")
        String puerta,
        @Description("Signatura del alojamiento")
        String signatura,
        @Description("Categoría del alojamiento")
        String categoria,
        @Description("Escalera del alojamiento")
        String escalera,
        @Description("Denominación del alojamiento")
        String denominacion,
        @Description("Código postal del alojamiento")
        String cdpostal,
        @Description("Localidad del alojamiento")
        String localidad,
        @Description("Tipo de alojamiento turístico")
        TipoAlojamiento alojamiento_tipo
) {}


