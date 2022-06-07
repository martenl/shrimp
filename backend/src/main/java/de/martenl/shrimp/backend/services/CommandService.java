package de.martenl.shrimp.backend.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class CommandService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CommandService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 20_000)
    void postToKafka() {
        System.out.println("hello");
        kafkaTemplate.send("command", "hello world");
    }
}
