package de.martenl.shrimp.backend.services;

import de.martenl.shrimp.backend.model.Pond;
import de.martenl.shrimp.backend.persistence.PondRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PondRepository pondRepository;
    private final Clock clock;

    public AnalyticsService(PondRepository pondRepository, Clock clock) {
        this.pondRepository = pondRepository;
        this.clock = clock;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Flux.interval(Duration.ofSeconds(30L))
                .flatMap(this::getOverdueMeasuringUnits)
                .subscribe(id -> logger.info("Measuring unit with id {} is overdue", id));
    }

    Flux<String> getOverdueMeasuringUnits(long l) {
        return pondRepository.findByLastUpdateBefore(Instant.now(clock).minus(2L, ChronoUnit.MINUTES))
                .flatMap(measuringUnit -> Mono.just(measuringUnit.getId()));
    }

    void computeAverages() {
        long count = pondRepository.count().block();
        int numberOfPages = (int) count / 10;
        if(count % numberOfPages != 0) numberOfPages++;
        for (int index = 0;index< numberOfPages;index++) {
            pondRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(index, 10));
                    //.map();
        }
    }

    void computeAverage(final Pond pond) {

    }
}
