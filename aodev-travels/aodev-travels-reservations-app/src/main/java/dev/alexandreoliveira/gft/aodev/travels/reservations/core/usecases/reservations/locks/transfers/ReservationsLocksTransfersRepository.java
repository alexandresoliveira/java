package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.transfers;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.TransferModel;

import java.util.List;
import java.util.UUID;

public interface ReservationsLocksTransfersRepository {

    ReservationModel lockTransfersForReservation(UUID reservationId, List<? extends TransferModel> transfers);
}
