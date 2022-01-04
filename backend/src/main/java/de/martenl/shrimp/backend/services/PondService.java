package de.martenl.shrimp.backend.services;

import de.martenl.shrimp.backend.model.PondInput;
import de.martenl.shrimp.backend.model.PondUpdate;
import de.martenl.shrimp.backend.model.CreatePondEvent;
import de.martenl.shrimp.backend.model.Pond;
import de.martenl.shrimp.backend.persistence.PondRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;

@Slf4j
public class PondService {

    private final PondRepository pondRepository;

    public PondService(PondRepository pondRepository) {
        this.pondRepository = pondRepository;
    }


    public Mono<Pond> createPond(final PondInput input) {
        var pond = new Pond(input.getName(), input.getMode());
        return pondRepository.insert(pond);
    }

    @EventListener(CreatePondEvent.class)
    public void handle(final CreatePondEvent createPondEvent) {
        log.info("creating pond with name {}", createPondEvent.getName());
    }

    public Mono<Pond> updatePond(final String id, final PondUpdate pondUpdate) {
        return pondRepository.findById(id)
                .map(p ->
                        p.updatePondConfiguration(pondUpdate.getUpdatedPondConfiguration())
                                .updatePondDimension(pondUpdate.getUpdatedPondDimension()))
                .flatMap(pondRepository::save);
    }
}
