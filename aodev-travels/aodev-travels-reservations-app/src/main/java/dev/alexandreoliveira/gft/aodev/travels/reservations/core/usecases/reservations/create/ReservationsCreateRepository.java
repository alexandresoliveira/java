package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.TransferModel;

public interface ReservationsCreateRepository {

    ReservationModel save(ReservationModel model);

    HotelModel save(HotelModel model);

    FlightModel save(FlightModel model);

    TransferModel save(TransferModel model);
}
