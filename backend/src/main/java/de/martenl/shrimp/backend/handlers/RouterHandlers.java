package de.martenl.shrimp.backend.handlers;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class RouterHandlers {

    private final AtomicBoolean mySwitch;
    private final WebClient webClient;

    public RouterHandlers(AtomicBoolean mySwitch, WebClient webClient) {
        this.mySwitch = mySwitch;
        this.webClient = webClient;
    }

    public Mono<ServerResponse> getRoute(){
        var response = webClient.get().uri("https://www.amazon.de").retrieve();
        response.toEntity(String.class).map(e -> e.getHeaders());
        return ok().contentType(MediaType.TEXT_HTML).bodyValue(response.bodyToMono(String.class));
        //return Mono.just("Hello world");
    }

    public Mono<Boolean> changeDeploymentEnvironment() {
        mySwitch.set(!mySwitch.get());
        return Mono.just(mySwitch.get());
    }
}
