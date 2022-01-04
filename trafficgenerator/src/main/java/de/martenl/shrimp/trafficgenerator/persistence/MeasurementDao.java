package de.martenl.shrimp.trafficgenerator.persistence;

import de.martenl.shrimp.trafficgenerator.domain.Measurement;
import de.martenl.shrimp.trafficgenerator.domain.MeasurementPrototype;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;

public class MeasurementDao {

    public Flux<Measurement> getMeasurements() {
        var measurements = List.of(
                new Measurement("abc123", Instant.now(), 0.05, 5.3),
                new Measurement("abc124", Instant.now(), 0.03, 6.3),
                new Measurement("abc125", Instant.now(), 0.07, 4.2),
                new Measurement("abc126", Instant.now(), 0.15, 5.03),
                new Measurement("abc127", Instant.now(), 0.09, 8.3),
                new Measurement("abc128", Instant.now(), 0.01, 5.13),
                new Measurement("abc129", Instant.now(), 0.05, 5.9),
                new Measurement("abc120", Instant.now(), 0.10, 3.3)
        );

        return Flux.fromIterable(measurements);
    }

    public Flux<MeasurementPrototype> getMeasurementPrototypes() {
        var measurements = List.of(
                new MeasurementPrototype("abc123", true, 0.05, 0.5,5.3, 0.5),
                new MeasurementPrototype("abc124", true, 0.03, 0.5,6.3, 0.1),
                new MeasurementPrototype("abc125", true, 0.07, 0.5,4.2, 2.0),
                new MeasurementPrototype("abc126", true, 0.15, 0.5,5.03, 0.1),
                new MeasurementPrototype("abc127", true, 0.09, 0.5,8.3, .6),
                new MeasurementPrototype("abc128", true, 0.01, 0.5,5.13, .01),
                new MeasurementPrototype("abc129", true, 0.05, 0.5,5.9, .14),
                new MeasurementPrototype("abc120", true, 0.10, 0.5,3.3, 1.5)
        );

        return Flux.fromIterable(measurements);
    }
}
