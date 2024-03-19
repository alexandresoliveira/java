package dev.alexandreoliveira.gft.aodev.travels.flights.core.models;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.groups.OnSeatReservation;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.groups.OnSeatsIndex;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;

import java.util.UUID;

public interface SeatModel extends BaseModel {

    @NotNull(groups = {Default.class, OnSeatsIndex.class, OnSeatReservation.class})
    FlightModel getFlight();

    @NotNull(groups = {OnSeatReservation.class})
    UUID getExternalId();

    @NotNull
    @NotEmpty
    String getSeatNumber();
}
