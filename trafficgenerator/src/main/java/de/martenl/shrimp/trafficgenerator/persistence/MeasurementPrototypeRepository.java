package de.martenl.shrimp.trafficgenerator.persistence;

import de.martenl.shrimp.trafficgenerator.domain.MeasurementPrototype;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MeasurementPrototypeRepository extends ReactiveMongoRepository<MeasurementPrototype, String> {
    Flux<MeasurementPrototype> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
