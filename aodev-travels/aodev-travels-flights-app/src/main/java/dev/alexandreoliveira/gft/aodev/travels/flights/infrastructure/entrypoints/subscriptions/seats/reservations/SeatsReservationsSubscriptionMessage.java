package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.subscriptions.seats.reservations;

import java.util.List;
import java.util.UUID;

public record SeatsReservationsSubscriptionMessage(
    List<Seat> seats
) {
    public record Seat(
        UUID flightId,
        UUID seatId,
        UUID reservationId
    ) {}
}
