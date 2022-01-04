package de.martenl.shrimp.trafficgenerator.config;

import de.martenl.shrimp.trafficgenerator.handlers.MeasurementHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    MeasurementHandlers measurementHandlers;

    @Bean
    RouterFunction<ServerResponse> indexRoute() {
        return route(GET("/"), sr -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring Webflux!")))
                .andRoute(GET("/measurement"), measurementHandlers::getMeasurementPrototypes)
                .andRoute(POST("/measurement"), measurementHandlers::createMeasurementPrototype)
                .andRoute(GET("/measurement/{id}"), measurementHandlers::getMeasurementPrototypeById)
                .andRoute(PUT("/measurement/{id}"), measurementHandlers::updateMeasurementPrototype)
                .andRoute(PUT("/measurement/{id}/enable"), measurementHandlers::enableMeasurementPrototype)
                .andRoute(DELETE("/measurement/{id}"), measurementHandlers::deleteMeasurementPrototypeById)
                .andRoute(GET("/hello"), sr -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromValue("Hello again, Spring Webflux!")));
    }


}
