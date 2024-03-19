package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.create;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;

public interface SeatsCreateRepository {

    SeatModel save(SeatModel model);
}
