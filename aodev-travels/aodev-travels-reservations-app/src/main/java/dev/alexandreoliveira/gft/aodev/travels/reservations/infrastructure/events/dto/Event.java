package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Event {
    default UUID eventId() { return UUID.randomUUID(); }
    default LocalDateTime eventCreatedAt() { return LocalDateTime.now(); }
}
