package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.lock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;

public interface HotelsLockRepository {

    HotelModel lockRoom(HotelModel hotelModel);
}
