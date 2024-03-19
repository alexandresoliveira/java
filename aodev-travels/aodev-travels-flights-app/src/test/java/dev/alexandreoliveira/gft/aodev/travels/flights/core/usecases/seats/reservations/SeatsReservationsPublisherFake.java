package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;

import java.util.List;
import java.util.UUID;

class SeatsReservationsPublisherFake implements SeatsReservationsPublisher {

    @Override
    public void publishLockSeatEvents(UUID registrationId, List<? extends SeatModel> seats) {
        System.out.println("Published!");
    }
}
