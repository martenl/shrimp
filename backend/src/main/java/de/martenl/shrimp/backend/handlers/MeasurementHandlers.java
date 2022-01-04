package de.martenl.shrimp.backend.handlers;

import de.martenl.shrimp.backend.model.Measurement;
import de.martenl.shrimp.backend.persistence.PondRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class MeasurementHandlers {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PondRepository pondRepository;

    public MeasurementHandlers(PondRepository pondRepository) {
        this.pondRepository = pondRepository;
    }

    public Mono<ServerResponse> postMeasurement(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        Mono<Measurement> measurementPrototypeMono = serverRequest.bodyToMono(Measurement.class);
        return pondRepository.findById(id)
                .flatMap(pond -> {
                    if (pond != null) logger.info("pond: {}", pond);
                    pond.addMeasurement(measurementPrototypeMono.block());
                    return pondRepository.save(pond);
                })
                .flatMap(pond -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond))
                .switchIfEmpty(notFound().build());
        //get measuring unit
        //if no measuring unit => 404
        //add measurement
        //save
        //send 201
    }
}
