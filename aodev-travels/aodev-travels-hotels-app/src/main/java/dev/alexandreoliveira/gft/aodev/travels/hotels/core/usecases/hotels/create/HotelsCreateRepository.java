package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.create;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;

public interface HotelsCreateRepository {

    HotelModel save(HotelModel model);
}
