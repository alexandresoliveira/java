package dev.alexandreoliveira.gft.aodev.travels.reservations.core.models;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface SeatModel extends BaseModel {

    @NotNull
    FlightModel getFlight();

    UUID getExternalId();

    @NotNull
    String getSeatNumber();
}
