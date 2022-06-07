package de.martenl.shrimp.frontend.config;

import java.time.Clock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import de.martenl.shrimp.frontend.handlers.SensorHandlers;

@Configuration
public class Beans {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    SensorHandlers sensorHandlers() {
        return new SensorHandlers(kafkaTemplate);
    }
}
