package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

class SeatsIndexRepositoryInMemory implements SeatsIndexRepository {

    private final Map<UUID, SeatModel> store;

    public SeatsIndexRepositoryInMemory() {
        this.store = new HashMap<>();
    }

    public SeatsIndexRepositoryInMemory(Map<UUID, SeatModel> store) {
        this.store = store;
    }

    @Override
    public List<SeatModel> findAvailableSeatsByFlight(SeatModel seatModel) {
        return store.values().stream().filter(model -> Objects.isNull(model.getExternalId()) && model.getFlight().equals(seatModel.getFlight())).toList();
    }
}
