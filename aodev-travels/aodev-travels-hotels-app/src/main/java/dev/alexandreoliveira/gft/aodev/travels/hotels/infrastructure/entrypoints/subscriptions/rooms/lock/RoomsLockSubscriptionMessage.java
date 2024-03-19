package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.lock;

import java.util.UUID;

public record RoomsLockSubscriptionMessage(
        UUID roomId,
        UUID hotelId,
        UUID reservationId
) {
}
