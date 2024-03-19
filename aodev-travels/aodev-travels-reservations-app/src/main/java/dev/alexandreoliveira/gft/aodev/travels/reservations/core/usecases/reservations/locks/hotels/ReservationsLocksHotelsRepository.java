package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.hotels;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;

import java.util.UUID;

public interface ReservationsLocksHotelsRepository {

    ReservationModel lockHotelForReservation(UUID reservationId, UUID hotelId);
}
