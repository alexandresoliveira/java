package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;

public interface RoomsLockReservationPublisher {

    void notifyForReservationLockRooms(RoomModel model);
}
