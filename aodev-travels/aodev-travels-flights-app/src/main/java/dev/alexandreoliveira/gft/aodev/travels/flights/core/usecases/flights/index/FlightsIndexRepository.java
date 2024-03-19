package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;

import java.util.List;

public interface FlightsIndexRepository {

    List<? extends FlightModel> findAllByParams(FlightModel flightModel);
}
