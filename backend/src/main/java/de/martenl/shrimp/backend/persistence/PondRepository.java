package de.martenl.shrimp.backend.persistence;

import de.martenl.shrimp.backend.model.Pond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.Instant;

public interface PondRepository extends ReactiveMongoRepository<Pond, String> {
    Flux<Pond> findAllByIdNotNullOrderByIdAsc(final Pageable page);;
    //Flux<Pond.PondData> findAllByIdNotNullOrderByIdAsc(final Pageable page);;
    Flux<Pond> findByLastUpdateBefore(Instant date);

}
