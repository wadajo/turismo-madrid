package org.wadajo.turismomadrid.domain.dto.cmadrid.enums;

public enum TipoAlojamiento {
    APART_TURISTICO("Apartamento turístico"),
    APARTAMENTO_RURAL("Apartamento rural"),
    CAMPING("Camping"),
    CASA_HUESPEDES("Casa de huéspedes"),
    CASA_RURAL("Casa rural"),
    HOSTAL("Hostal"),
    HOSTERIAS("Hostería"),
    HOTEL("Hotel"),
    HOTEL_APART("Apart-hotel"),
    HOTEL_RURAL("Hotel rural"),
    PENSION("Pensión"),
    VIVIENDAS_TURISTICAS("Vivienda de uso turístico (Airbnb o sim)");

    public final String printValue;

    TipoAlojamiento(String printValue) {
        this.printValue = printValue;
    }
}
