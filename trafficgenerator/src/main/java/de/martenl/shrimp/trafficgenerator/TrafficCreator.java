package de.martenl.shrimp.trafficgenerator;

import de.martenl.shrimp.trafficgenerator.domain.Measurement;
import de.martenl.shrimp.trafficgenerator.domain.MeasurementPrototype;
import de.martenl.shrimp.trafficgenerator.persistence.MeasurementDao;
import de.martenl.shrimp.trafficgenerator.services.MeasurementPrototypesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Random;

public class TrafficCreator implements InitializingBean {

    private static final String BACKEND_URI = "/pond/id/measurement";
    private static final Logger logger = LoggerFactory.getLogger(TrafficCreator.class);

    private final WebClient webClient;
    private final MeasurementDao measurementDao;
    private final MeasurementPrototypesService measurementPrototypesService;
    private final Random rnd;

    public TrafficCreator(final WebClient webClient, final MeasurementDao measurementDao, final MeasurementPrototypesService measurementPrototypesService) {
        this.webClient = webClient;
        this.measurementDao = measurementDao;
        this.measurementPrototypesService = measurementPrototypesService;
        this.rnd = new Random();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Flux.interval(Duration.ofSeconds(30L))
                .flatMap(this::getMeasurementPrototypes)
                .flatMap(this::createMeasurement)
                .subscribe(this::sendTraffic);
    }

    Flux<MeasurementPrototype> getMeasurementPrototypes(long n) {
        return measurementPrototypesService.getAllMeasurementPrototypes();
    }

    Mono<Measurement> createMeasurement(final MeasurementPrototype measurementPrototype) {
        if (!measurementPrototype.isEnabled()) {
            return Mono.empty();
        }
        var salinity = rnd.nextGaussian() * measurementPrototype.getSalinityVariance() + measurementPrototype.getSalinityMid();
        var pH = rnd.nextGaussian() * measurementPrototype.getpHVariance() + measurementPrototype.getpHMid();
        return Mono.just(new Measurement(measurementPrototype.getMeasuringUnitId(), Instant.now(), salinity, pH));
    }

    void sendTraffic(Measurement traffic) {
        logger.info(traffic.toString());
        webClient.post()
                .uri(BACKEND_URI.replaceFirst("id", traffic.getMeasuringUnitId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(traffic)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    logger.info("Client error: {}", response.statusCode());
                    return Mono.just(new Exception("Client error"));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    logger.info("Server error: {}", response.statusCode());
                    return Mono.just(new Exception("Server error"));
                })
                .bodyToMono(String.class)
                .subscribe(logger::info);
    }
}
