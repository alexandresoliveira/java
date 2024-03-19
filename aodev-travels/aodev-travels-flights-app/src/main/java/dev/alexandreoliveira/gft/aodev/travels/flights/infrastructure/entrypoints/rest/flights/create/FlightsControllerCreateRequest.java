package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FlightsControllerCreateRequest(
        @NotNull @NotEmpty String company,
        @NotNull Integer flightNumber,
        @NotNull @NotEmpty List<Seat> seats,
        @NotNull @NotEmpty String from,
        @NotNull @NotEmpty String to,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkIn,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkOut,
        @NotNull BigDecimal price
) {

    public record Seat(
            @NotNull @NotEmpty String seatNumber
    ) {}
}
