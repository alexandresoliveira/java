package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class FlightsIndexRepositoryInMemory implements FlightsIndexRepository {

    private final Map<UUID, FlightModel> store;

    public FlightsIndexRepositoryInMemory() {
        this.store = new HashMap<>();
    }

    public FlightsIndexRepositoryInMemory(Map<UUID, FlightModel> store) {
        this.store = store;
    }

    @Override
    public List<FlightModel> findAllByParams(FlightModel flightModel) {
        return store.values().stream().filter(model -> model.getOrigin().toLowerCase().contains(flightModel.getOrigin().toLowerCase()) && model.getDestiny().toLowerCase().contains(flightModel.getDestiny().toLowerCase())).toList();
    }
}
