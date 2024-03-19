package dev.alexandreoliveira.gft.aodev.travels.reservations.core.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ReservationModel extends BaseModel {

    @NotNull
    UUID getUserId();

    @NotNull
    @NotEmpty
    String getUserName();

    @NotNull
    HotelModel getHotel();

    @NotNull
    @NotEmpty
    List<? extends TransferModel> getTransfers();

    @NotNull
    @NotEmpty
    List<? extends FlightModel> getFlights();

    Boolean getStatus();

    BigDecimal getPrice();
}
