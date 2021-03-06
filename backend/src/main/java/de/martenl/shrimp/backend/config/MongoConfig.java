package de.martenl.shrimp.backend.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Value( "${mongo.url:mongodb://127.0.0.1:27017}" )
    String mongoUrl;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUrl);
    }
}
