package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.create;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;

public interface RoomsCreateRepository {

    RoomModel save(RoomModel roomModel);
}
