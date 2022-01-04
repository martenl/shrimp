package de.martenl.shrimp.backend.services;

import de.martenl.shrimp.backend.model.Measurement;
import de.martenl.shrimp.backend.model.Pond;
import de.martenl.shrimp.backend.persistence.PondRepository;
import reactor.core.publisher.Mono;

public class MeasurementService {

    private final PondRepository pondRepository;

    public MeasurementService(PondRepository pondRepository) {
        this.pondRepository = pondRepository;
    }


    public Mono<Pond> addMeasurement(Measurement measurement) {
        return pondRepository.findById(measurement.getMeasuringUnitId())
                .flatMap(pond -> Mono.just(pond.addMeasurement(measurement)))
                .flatMap(pondRepository::save);


    }
}
