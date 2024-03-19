package dev.alexandreoliveira.gft.aodev.travels.reservations.core.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HotelModel extends BaseModel {

    @NotNull
    UUID getExternalId();

    @NotNull
    ReservationModel getReservation();

    @NotNull
    @NotEmpty
    String getName();

    @NotNull
    @NotEmpty
    String getRoom();

    @NotNull
    List<? extends GuestModel> getGuests();

    @NotNull
    LocalDateTime getCheckIn();

    @NotNull
    LocalDateTime getCheckOut();

    Boolean getStatus();

    @NotNull
    BigDecimal getPrice();
}
