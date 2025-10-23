package org.wadajo.turismomadrid.application.client;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.ws.rs.ConstrainedTo;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;
import org.wadajo.turismomadrid.domain.dto.cmadrid.AlojamientosTuristicosResponseDto;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Provider
@ConstrainedTo(RuntimeType.CLIENT)
public class AlojamientosTuristicosResponseDtoBodyHandler implements MessageBodyReader<AlojamientosTuristicosResponseDto> {
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return type == AlojamientosTuristicosResponseDto.class;
    }

    @Override
    public AlojamientosTuristicosResponseDto readFrom(Class<AlojamientosTuristicosResponseDto> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return new JsonMapper().readValue(body, AlojamientosTuristicosResponseDto.class);
    }
}
