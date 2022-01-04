package de.martenl.shrimp.trafficgenerator.handlers;

import de.martenl.shrimp.trafficgenerator.domain.MeasurementPrototype;
import de.martenl.shrimp.trafficgenerator.persistence.MeasurementDao;
import de.martenl.shrimp.trafficgenerator.services.MeasurementPrototypesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static reactor.core.publisher.MonoExtensionsKt.toMono;

public class MeasurementHandlers {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Validator validator = new MeasurementPrototype.MeasurementPrototypeValidator();

    private final MeasurementDao measurementDao;
    private final MeasurementPrototypesService measurementPrototypesService;

    public MeasurementHandlers(final MeasurementDao measurementDao, final MeasurementPrototypesService measurementPrototypesService) {
        this.measurementDao = measurementDao;
        this.measurementPrototypesService = measurementPrototypesService;
    }

    //get all Measurement prototypes
    public Mono<ServerResponse> getMeasurementPrototypes(final ServerRequest serverRequest) {
        final int page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);

        var measurementPrototype = measurementPrototypesService.getMeasurementPrototypePage(page);//measurementDao.getMeasurementPrototypes();
        return ok().contentType(MediaType.APPLICATION_JSON).body(measurementPrototype, MeasurementPrototype.class);
    }

    //get Measurement prototype by id
    public Mono<ServerResponse> getMeasurementPrototypeById(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        return measurementPrototypesService.getById(id)
                .flatMap(measurementPrototype -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(measurementPrototype))
                .switchIfEmpty(ServerResponse.notFound().build());
        /*return measurementDao.getMeasurementPrototypes()
                .filter(mp -> mp.getId().equals(id))
                .last()
                .flatMap(measurementPrototype -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(measurementPrototype))
                .switchIfEmpty(ServerResponse.notFound().build());*/
    }

    //create Measurement prototype
    public Mono<ServerResponse> createMeasurementPrototype(final ServerRequest serverRequest) {
        Mono<MeasurementPrototype> measurementPrototypeMono = serverRequest.bodyToMono(MeasurementPrototype.class).doOnNext(this::validate);

        return measurementPrototypesService.create(measurementPrototypeMono)
                .flatMap(measurementPrototype -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(measurementPrototype));
    }

    /*
    req.body(toMono(Employee.class))
        .doOnNext(employeeRepository()::updateEmployee)
        .then(ok().build())));
     */
    //update measurement prototype
    public Mono<ServerResponse> updateMeasurementPrototype(final ServerRequest serverRequest) {
        logger.info("updating");
        Mono<MeasurementPrototype> measurementPrototypeMono = null;
        return serverRequest.bodyToMono(MeasurementPrototype.class)
                .map(measurementPrototypesService::update)
                .flatMap(measurementPrototypeMono1 -> ok().bodyValue(measurementPrototypeMono1));
    }

    public Mono<ServerResponse> enableMeasurementPrototype(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        return measurementPrototypesService.enableMesurementPrototype(id).flatMap(v -> ok().build());
    }

    public Mono<ServerResponse> deleteMeasurementPrototypeById(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        logger.info("deleting {}", id);
        return measurementPrototypesService.delete(id).flatMap(v -> ok().build());
    }

    private void validate(MeasurementPrototype measurementPrototype) {
        Errors errors = new BeanPropertyBindingResult(measurementPrototype, "measurementPrototype");
        validator.validate(measurementPrototype, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
