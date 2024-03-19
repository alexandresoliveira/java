package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.create;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class FlightsCreateRepositoryInMemory implements FlightsCreateRepository {
    private final Map<UUID, FlightModel> store;

    public FlightsCreateRepositoryInMemory() {
        this.store = new HashMap<>();
    }

    @Override
    public FlightModel save(FlightModel model) {
        LocalDateTime now = LocalDateTime.now();

        var entity = (FlightEntity) model;
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(now);
        entity.setCreatedBy("Flight Test App");
        entity.setVersion(now);

        store.put(entity.getId(), entity);

        return entity;
    }
}
