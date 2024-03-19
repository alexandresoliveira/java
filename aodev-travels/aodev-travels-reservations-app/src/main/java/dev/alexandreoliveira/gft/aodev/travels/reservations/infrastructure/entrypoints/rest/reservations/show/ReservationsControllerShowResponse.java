package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.show;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationsControllerShowResponse(
        UUID id,
        UUID userId,
        String name,
        Hotel hotel,
        List<Flight> flights,
        List<Transfer> transfers,
        Boolean status,
        BigDecimal price
) {

    public record Hotel(
            UUID id,
            UUID externalId,
            String name,
            UUID roomId,
            List<Guest> guests,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            Boolean status,
            BigDecimal price
    ) {}

    public record Guest(
            UUID id,
            String name,
            Integer age
    ) {}

    public record Transfer(
            UUID id,
            UUID externalId,
            String carInfo,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            Boolean status,
            BigDecimal price
    ) {}

    public record Flight(
            UUID id,
            UUID externalId,
            String company,
            Integer flightNumber,
            List<Seat> seats,
            String origin,
            String destiny,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            Boolean status,
            BigDecimal price
    ) {}

    public record Seat(
            UUID id,
            UUID externalId,
            String seatNumber
    ) {}
}
