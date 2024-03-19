package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.unlock;

import java.util.UUID;

public record RoomsUnlockSubscriptionMessage(
        UUID roomId,
        UUID hotelId,
        UUID reservationId
) {
}
