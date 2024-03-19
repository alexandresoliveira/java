package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.update;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.exceptions.CoreException;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.commands.DestinationsUpdateCommand;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.ModelValidatorUtil;

import java.util.List;

public class DestinationsUpdateUseCase implements IUseCase.In<DestinationModel> {

    private final DestinationsUpdateCommand command;

    public DestinationsUpdateUseCase(DestinationsUpdateCommand command) {
        this.command = command;
    }

    @Override
    public void execute(DestinationModel input) {
        List<String> errors = ModelValidatorUtil.isValid(input);
        if (!errors.isEmpty()) {
            throw new CoreException(
                    errors,
                    getClass().getName(),
                    "Error to update Destination"
            );
        }
        command.publishUpdate(input);
    }
}
