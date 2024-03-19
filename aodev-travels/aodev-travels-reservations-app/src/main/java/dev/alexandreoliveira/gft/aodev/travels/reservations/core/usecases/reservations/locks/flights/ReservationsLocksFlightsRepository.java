package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.flights;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;

import java.util.List;
import java.util.UUID;

public interface ReservationsLocksFlightsRepository {

    ReservationModel lockFlightsForReservation(UUID reservationId, List<? extends FlightModel> flights);
}
