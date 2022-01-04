package de.martenl.shrimp.backend.config;

import de.martenl.shrimp.backend.handlers.MeasurementHandlers;
import de.martenl.shrimp.backend.handlers.PondHandlers;
import org.springframework.beans.factory.annotation.Autowired;
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
    PondHandlers pondHandlers;

    @Autowired
    MeasurementHandlers measurementHandlers;

    @Bean
    RouterFunction<ServerResponse> indexRoute() {
        return route(GET("/"), sr -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring Webflux!")))
                .andRoute(GET("/sensor"), pondHandlers::getPonds)
                .andRoute(GET("/sensor/{id}"), pondHandlers::getPondById)
                .andRoute(POST("/sensor"), pondHandlers::createPond)
                .andRoute(POST("/sensor/{id}"), measurementHandlers::postMeasurement)
                .andRoute(GET("/pond"), pondHandlers::getPonds)
                .andRoute(GET("/pond/exception"), pondHandlers::getException)
                .andRoute(GET("/pond/{id}"), pondHandlers::getPondById)
                .andRoute(PATCH("/pond/{id}"), pondHandlers::updatePond)
                .andRoute(POST("/pond"), pondHandlers::createPond)
                .andRoute(POST("/pond/{id}/measurement"), measurementHandlers::postMeasurement)
                .andRoute(GET("/hello"), sr -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromValue("Hello again, Spring Webflux!")));
    }
}
