package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.commands;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;

public interface DestinationsUpdateCommand {

    void publishUpdate(DestinationModel model);
}
