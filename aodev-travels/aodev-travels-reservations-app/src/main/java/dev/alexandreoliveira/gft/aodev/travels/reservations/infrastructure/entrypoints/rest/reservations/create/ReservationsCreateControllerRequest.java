package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationsCreateControllerRequest(
        @NotNull UUID userId,
        @NotNull @NotEmpty String userName,
        @NotNull Hotel hotel,
        @NotNull @NotEmpty List<Transfer> transfers,
        @NotNull @NotEmpty List<Flight> flights
        ) {

    public record Hotel(
            @NotNull UUID externalId,
            @NotNull @NotEmpty String name,
            @NotNull UUID roomId,
            List<Guest> guests,
            @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkIn,
            @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkOut,
            @NotNull BigDecimal price
    ) {}

    public record Guest(
            @NotNull @NotEmpty String name,
            @NotNull Integer age
    ) {}

    public record Transfer(
            @NotNull UUID externalId,
            @NotNull @NotEmpty String carInfo,
            @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkIn,
            @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkOut,
            @NotNull BigDecimal price
    ) {}

    public record Flight(
            @NotNull UUID externalId,
            @NotNull @NotEmpty String company,
            @NotNull @NotEmpty Integer flightNumber,
            @NotNull @NotEmpty List<Seat> seats,
            @NotNull @NotEmpty String origin,
            @NotNull @NotEmpty String destiny,
            @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkIn,
            @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ") LocalDateTime checkOut,
            @NotNull BigDecimal price
    ) {}

    public record Seat(
            @NotNull UUID externalId,
            @NotNull @NotEmpty String seatNumber
    ) {}
}
