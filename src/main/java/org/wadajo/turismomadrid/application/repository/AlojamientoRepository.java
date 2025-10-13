package org.wadajo.turismomadrid.application.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.wadajo.turismomadrid.domain.document.AlojamientoDocument;

@ApplicationScoped
public class AlojamientoRepository implements PanacheMongoRepository<AlojamientoDocument> {

}
