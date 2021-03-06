package de.martenl.shrimp.frontend.handlers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;


public class SensorHandlers {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SensorHandlers(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 20_000)
    void postToKafka() {
        System.out.println("hello kafka");
        kafkaTemplate.send("command", "hello kafka");
    }
}
