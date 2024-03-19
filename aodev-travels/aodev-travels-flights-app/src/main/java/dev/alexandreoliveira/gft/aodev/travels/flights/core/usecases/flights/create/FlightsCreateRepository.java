package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.create;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;

public interface FlightsCreateRepository {

    FlightModel save(FlightModel model);
}
