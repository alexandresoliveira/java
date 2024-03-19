package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;

import java.util.UUID;

public interface RoomsLockRepository {

    RoomModel lock(UUID roomId, UUID hotelId, UUID reservationId, Boolean available);
}
