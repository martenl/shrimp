package de.martenl.shrimp.frontend.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CommandService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CommandService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Scheduled(fixedDelay = 1000L)
    public void sendCommand() {

        //System.out.println("my command");
        kafkaTemplate.send("command", "key", "start the aereator");
    }
}
