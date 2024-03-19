package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.update;

import java.util.UUID;

public record DestinationsUpdateUseCaseOutput(
        UUID id,
        String city,
        String country
) {
}
