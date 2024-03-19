package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

class SeatsReservationsRepositoryInMemory implements SeatsReservationsRepository {

    private final Map<UUID, SeatModel> store;

    public SeatsReservationsRepositoryInMemory(Map<UUID, SeatModel> store) {
        this.store = store;
    }

    @Override
    public SeatModel reservationSeats(SeatModel seat) {
        boolean isValidToReserve = store.values().stream().anyMatch(model ->
                model.getFlight().equals(seat.getFlight()) &&
                Objects.isNull(model.getExternalId()) &&
                model.getSeatNumber().equalsIgnoreCase(seat.getSeatNumber()));

        if (isValidToReserve) {
            var entity = (SeatEntity) store.get(seat.getId());
            entity.setExternalId(seat.getExternalId());
            store.put(seat.getId(), entity);
            return entity;
        }

        throw new RuntimeException("Error to reserve a seat. This seat has reserved.");
    }
}
