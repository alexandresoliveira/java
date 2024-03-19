package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.index;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;

import java.util.List;
import java.util.UUID;

public record DestinationsControllerIndexResponse(
        List<Destination> destinations
) {

    public record Destination(
            UUID id,
            String city,
            String state
    ) {
        public Destination(DestinationModel model) {
            this(model.getId(), model.getCity(), model.getState());
        }
    }
}
