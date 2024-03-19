package dev.alexandreoliveira.gft.aodev.travels.flights.core.models;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BaseModel {

    UUID getId();

    LocalDateTime getCreatedAt();

    String getCreatedBy();

    LocalDateTime getUpdatedAt();

    String getUpdatedBy();

    LocalDateTime getVersion();
}
