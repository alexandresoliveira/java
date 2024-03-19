package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.subscriptions.rooms.lock;

import java.util.List;
import java.util.UUID;

public record TransfersLockSubscriptionMessage(
        List<UUID> transfers,
        UUID reservationId
) {}
