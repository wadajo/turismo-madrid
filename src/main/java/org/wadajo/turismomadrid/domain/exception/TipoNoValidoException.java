package org.wadajo.turismomadrid.domain.exception;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("tipo_no_valido")
public class TipoNoValidoException extends RuntimeException {
    public TipoNoValidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
