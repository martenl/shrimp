package de.martenl.shrimp.trafficgenerator.config;

import de.martenl.shrimp.trafficgenerator.TrafficCreator;
import de.martenl.shrimp.trafficgenerator.handlers.MeasurementHandlers;
import de.martenl.shrimp.trafficgenerator.persistence.MeasurementDao;
import de.martenl.shrimp.trafficgenerator.persistence.MeasurementPrototypeRepository;
import de.martenl.shrimp.trafficgenerator.services.MeasurementPrototypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Beans {

    @Bean
    public ResourceBundleMessageSource messageSource() {

        var source = new ResourceBundleMessageSource();
        source.setBasenames("messages/messages");
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }

    @Autowired
    MeasurementPrototypeRepository measurementPrototypeRepository;

    @Bean
    WebClient webclient() {
        return WebClient.builder()
                .baseUrl("http://shrimp-backend:8089")
                .build();
    }

    @Bean
    MeasurementDao measurementDao() {
        return new MeasurementDao();
    }

    @Bean
    MeasurementPrototypesService measurementPrototypesService(MeasurementPrototypeRepository measurementPrototypeRepository) {
        return new MeasurementPrototypesService(measurementPrototypeRepository);
    }

    @Bean
    MeasurementHandlers measurementHandlers(MeasurementDao measurementDao, MeasurementPrototypesService measurementPrototypesService) {
        return new MeasurementHandlers(measurementDao, measurementPrototypesService);
    }

    @Bean
    TrafficCreator trafficCreator(WebClient webClient, MeasurementDao measurementDao, MeasurementPrototypesService measurementPrototypesService) {
        return new TrafficCreator(webClient, measurementDao, measurementPrototypesService);
    }
}
