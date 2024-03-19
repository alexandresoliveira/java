package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.unlock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;

import java.util.UUID;

public interface RoomsUnlockRepository {

    RoomModel unlockRoom(UUID roomId, UUID hotelId, UUID reservationId, Boolean available);
}
