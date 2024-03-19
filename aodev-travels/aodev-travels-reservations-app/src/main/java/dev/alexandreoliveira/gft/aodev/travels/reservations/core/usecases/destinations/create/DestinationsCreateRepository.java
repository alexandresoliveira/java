package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;

public interface DestinationsCreateRepository {

    DestinationModel save(DestinationModel model);
}
