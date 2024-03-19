package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;

public interface SeatsReservationsRepository {

    SeatModel reservationSeats(SeatModel seat);
}
