package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.hotels;

import java.util.UUID;

public record ReservationsLocksHotelsSubscriptionMessage(
        UUID reservationId,
        UUID hotelId,
        UUID roomId
) {
}
