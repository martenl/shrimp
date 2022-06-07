package de.martenl.shrimp.backend.handlers;

import de.martenl.shrimp.backend.model.Measurement;
import de.martenl.shrimp.backend.model.Pond;
import de.martenl.shrimp.backend.persistence.PondRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
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
        return serverRequest.bodyToMono(Measurement.class)
                .zipWith(pondRepository.findById(id))
                .flatMap(tuple -> {
                    Measurement measurement = tuple.getT1();
                    Pond pond = tuple.getT2();
                    logger.info("pond: {}", pond);
                    pond.addMeasurement(measurement);
                    return pondRepository.save(pond);
                })
                .flatMap(pond -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond))
                .switchIfEmpty(notFound().build());
        /*return pondRepository.findById(id)
                .flatMap(pond -> {
                    if (pond != null) logger.info("pond: {}", pond);
                    pond.addMeasurement(measurementPrototypeMono.block());
                    return pondRepository.save(pond);
                })
                .flatMap(pond -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond))
                .switchIfEmpty(notFound().build());*/
        //get measuring unit
        //if no measuring unit => 404
        //add measurement
        //save
        //send 201
    }

    public Mono<ServerResponse> getMeasurements(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        final var page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);

        return ServerResponse.ok().body(Flux.just(1,2,3), Integer.class);
    }
}
