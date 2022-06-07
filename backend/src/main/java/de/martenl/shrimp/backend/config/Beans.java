package de.martenl.shrimp.backend.config;

import de.martenl.shrimp.backend.handlers.MeasurementHandlers;
import de.martenl.shrimp.backend.handlers.PondHandlers;
import de.martenl.shrimp.backend.handlers.RouterHandlers;
import de.martenl.shrimp.backend.persistence.PondRepository;
import de.martenl.shrimp.backend.services.AnalyticsService;
import de.martenl.shrimp.backend.services.CommandService;
import de.martenl.shrimp.backend.services.PondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Clock;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class Beans {

    @Autowired
    PondRepository pondRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    WebClient webclient() {
        return WebClient.builder()
                //.baseUrl("http://shrimp-backend:8089")
                .build();
    }

    @Bean
    PondService pondService(PondRepository pondRepository) {
        return new PondService(pondRepository);
    }

    @Bean
    AnalyticsService analyticsService(PondRepository pondRepository, Clock clock) {
        return new AnalyticsService(pondRepository, clock);
    }

    @Bean
    PondHandlers pondHandlers(PondRepository pondRepository, PondService pondService, ApplicationEventPublisher applicationEventPublisher) {
        return new PondHandlers(pondRepository, pondService, applicationEventPublisher/*, kafkaTemplate*/);
    }

    @Bean
    MeasurementHandlers measurementHandlers(PondRepository pondRepository) {
        return new MeasurementHandlers(pondRepository);
    }

    @Bean
    RouterHandlers routerHandlers(WebClient webClient) {
        var mySwitch = new AtomicBoolean(false);
        return new RouterHandlers(mySwitch, webClient);
    }

    @Bean
    CommandService commandService(KafkaTemplate<String, Object> kafkaTemplate) {
        return new CommandService(kafkaTemplate);
    }
}
