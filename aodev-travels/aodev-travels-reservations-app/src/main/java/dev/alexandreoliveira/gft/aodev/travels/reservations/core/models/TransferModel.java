package dev.alexandreoliveira.gft.aodev.travels.reservations.core.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface TransferModel extends BaseModel {

    @NotNull
    ReservationModel getReservation();

    @NotNull
    UUID getExternalId();

    @NotNull
    @NotEmpty
    String getCarInfo();

    @NotNull
    LocalDateTime getCheckIn();

    @NotNull
    LocalDateTime getCheckOut();

    Boolean getStatus();

    @NotNull
    BigDecimal getPrice();
}
