package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.Event;

import java.util.UUID;

public record UpdateDestinationEvent(
        UUID id,
        String city,
        String state
) implements Event {}
