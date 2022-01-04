package de.martenl.shrimp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        //SpringApplication.run(BackendApplication.class, args);
        var charFlux = Flux.fromArray("a b c d e f g h i j".split(" "));
        var myFlux = Flux.fromIterable(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                .zipWith(charFlux, (i, c) -> String.format("number: %d char: %s", i, c))
                .log();
                //.map(t -> String.format("number: %d char: %s", t.getT1(), t.getT2()));

        myFlux.subscribe(System.out::println, System.err::println, () -> System.out.println("I'm done"));
        charFlux.subscribe(System.out::println, System.err::println, () -> System.out.println("I'm done"));
    }

}
