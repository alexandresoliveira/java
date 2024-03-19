package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.index;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class HotelsIndexRepositoryInMemory implements HotelsIndexRepository {

    private final Map<UUID, HotelModel> store;

    public HotelsIndexRepositoryInMemory() {
        this.store = new HashMap<>();
    }

    public HotelsIndexRepositoryInMemory(Map<UUID, HotelModel> store) {
        this.store = store;
    }

    @Override
    public List<? extends HotelModel> findByHotelParams(HotelModel hotelModel) {
        return store.values().stream().filter(model ->
                model.getCity().toLowerCase().contains(hotelModel.getCity().toLowerCase()) ||
                model.getState().toLowerCase().contains(hotelModel.getState().toLowerCase()) ||
                model.getName().toLowerCase().contains(hotelModel.getName().toLowerCase())
        ).toList();
    }
}
