package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.update;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;

public interface DestinationsUpdateRepository {

    DestinationModel update(DestinationModel model);
}
