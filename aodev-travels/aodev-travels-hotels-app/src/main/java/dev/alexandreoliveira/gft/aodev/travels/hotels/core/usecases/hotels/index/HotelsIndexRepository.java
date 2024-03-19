package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.index;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;

import java.util.List;

public interface HotelsIndexRepository {

    List<? extends HotelModel> findByHotelParams(HotelModel model);
}
