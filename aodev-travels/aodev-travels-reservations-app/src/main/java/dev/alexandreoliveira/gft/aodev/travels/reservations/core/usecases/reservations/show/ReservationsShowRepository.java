package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.show;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;

public interface ReservationsShowRepository {

    ReservationModel findById(ReservationModel model);
}
