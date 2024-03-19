package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.create;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class SeatsCreateRepositoryInMemory implements SeatsCreateRepository {

    private final Map<UUID, SeatModel> store;

    public SeatsCreateRepositoryInMemory() {
        this.store = new HashMap<>();
    }

    @Override
    public SeatModel save(SeatModel model) {
        LocalDateTime now = LocalDateTime.now();

        var entity = (SeatEntity) model;
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(now);
        entity.setCreatedBy("Flight App Test");
        entity.setVersion(now);

        store.put(entity.getId(), entity);

        return entity;
    }
}
