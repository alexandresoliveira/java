package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.index;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;

import java.util.List;

public interface DestinationsIndexRepository {

    List<? extends DestinationModel> findAllByParams(DestinationModel model);
}
