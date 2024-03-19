package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.update;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;

import java.util.UUID;

public record DestinationsControllerUpdateResponse(
        UUID id,
        String city,
        String state
) {

    public DestinationsControllerUpdateResponse(DestinationModel model) {
        this(model.getId(), model.getCity(), model.getState());
    }
}
