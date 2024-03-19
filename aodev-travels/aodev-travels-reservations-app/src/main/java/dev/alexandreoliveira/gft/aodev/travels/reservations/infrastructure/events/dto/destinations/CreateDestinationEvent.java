package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.Event;

public record CreateDestinationEvent(
        String city,
        String state
) implements Event {}
