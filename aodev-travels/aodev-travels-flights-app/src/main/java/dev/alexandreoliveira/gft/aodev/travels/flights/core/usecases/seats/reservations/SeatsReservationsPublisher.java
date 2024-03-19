package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;

import java.util.List;
import java.util.UUID;

public interface SeatsReservationsPublisher {

    void publishLockSeatEvents(UUID registrationId, List<? extends SeatModel> seats);
}
