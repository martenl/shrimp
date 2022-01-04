package de.martenl.shrimp.trafficgenerator.services;

import de.martenl.shrimp.trafficgenerator.domain.MeasurementPrototype;
import de.martenl.shrimp.trafficgenerator.persistence.MeasurementPrototypeRepository;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MeasurementPrototypesService {

    public static final int PAGE_SIZE = 20;
    private final MeasurementPrototypeRepository measurementPrototypeRepository;

    public MeasurementPrototypesService(MeasurementPrototypeRepository measurementPrototypeRepository) {
        this.measurementPrototypeRepository = measurementPrototypeRepository;
    }

    public Mono<MeasurementPrototype> create(final Mono<MeasurementPrototype> measurementPrototype) {
        return measurementPrototypeRepository.insert(measurementPrototype).last();
    }

    public Mono<MeasurementPrototype> getById(final String id) {
        return measurementPrototypeRepository.findById(id);
    }

    public Flux<MeasurementPrototype> getAllMeasurementPrototypes() {
        return measurementPrototypeRepository.findAll();
    }

    public Flux<MeasurementPrototype> getMeasurementPrototypePage(final int page) {
        var pageable = PageRequest.of(page, PAGE_SIZE);
        return measurementPrototypeRepository.findAllByIdNotNullOrderByIdAsc(pageable);
    }

    public Mono<MeasurementPrototype> update(final MeasurementPrototype measurementPrototype) {
        return measurementPrototypeRepository.save(measurementPrototype);
    }

    public Mono<Void> delete(final String id) {
        return measurementPrototypeRepository.deleteById(id);
    }

    public Mono<MeasurementPrototype> enableMesurementPrototype(String id) {
        return measurementPrototypeRepository.findById(id)
                .map(MeasurementPrototype::changeEnabled)
                .flatMap(measurementPrototypeRepository::save);
    }
}
