package dev.alexandreoliveira.gft.aodev.travels.reservations.core.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FlightModel extends BaseModel {

    @NotNull
    ReservationModel getReservation();

    @NotNull
    UUID getExternalId();

    @NotNull
    @NotEmpty
    String getCompany();

    @NotNull
    Integer getFlightNumber();

    @NotNull
    List<? extends SeatModel> getSeats();

    @NotNull
    @NotEmpty
    String getOrigin();

    @NotNull
    @NotEmpty
    String getDestiny();

    @NotNull
    LocalDateTime getCheckIn();

    @NotNull
    LocalDateTime getCheckOut();

    Boolean getStatus();

    @NotNull
    BigDecimal getPrice();
}
