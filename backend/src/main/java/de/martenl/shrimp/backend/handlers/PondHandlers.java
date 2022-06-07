package de.martenl.shrimp.backend.handlers;

import de.martenl.shrimp.backend.model.Pond;
import de.martenl.shrimp.backend.model.PondInput;
import de.martenl.shrimp.backend.model.PondUpdate;
import de.martenl.shrimp.backend.persistence.PondRepository;
import de.martenl.shrimp.backend.services.PondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class PondHandlers {

    public static final int PAGE_SIZE = 20;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PondRepository pondRepository;
    private final PondService pondService;
    private final ApplicationEventPublisher applicationEventPublisher;
    //private final KafkaTemplate<String, String> kafkaTemplate;

    public PondHandlers(PondRepository pondRepository, PondService pondService, ApplicationEventPublisher applicationEventPublisher/*, KafkaTemplate<String, String> kafkaTemplate*/) {
        this.pondRepository = pondRepository;
        this.pondService = pondService;
        this.applicationEventPublisher = applicationEventPublisher;
        //this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<ServerResponse> getPonds(final ServerRequest serverRequest) {
        final var page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
        var ponds = pondRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, PAGE_SIZE));
        return ok().contentType(MediaType.APPLICATION_JSON).body(ponds, Pond.class);
    }

    public Mono<ServerResponse> getPondById(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        return pondRepository.findById(id)
                .flatMap(pond -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getException(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        return pondRepository.findById(id)
                .flatMap(pond -> {
                    if (true) throw new IllegalStateException("Illegal state");
                    return ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.status(501).build());
    }

    public Mono<ServerResponse> createPond(final ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(PondInput.class)
                .flatMap(pondService::createPond)
                .flatMap(pond -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond));
    }

    public Mono<ServerResponse> updatePond(final ServerRequest serverRequest) {
        final var id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(PondUpdate.class)
                .log()
                .flatMap(pondUpdate -> pondService.updatePond(id, pondUpdate))
                .flatMap(pond -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pond));
    }

    public Mono<ServerResponse> sendCommand(final ServerRequest serverRequest) {
        //kafkaTemplate.send("command", "I command thy");

        return ServerResponse.ok().build();
    }
}
