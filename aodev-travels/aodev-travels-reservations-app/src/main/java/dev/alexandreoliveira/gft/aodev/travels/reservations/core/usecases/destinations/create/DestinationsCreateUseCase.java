package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.exceptions.CoreException;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.commands.DestinationsCreateCommand;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.ModelValidatorUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DestinationsCreateUseCase implements IUseCase.In<DestinationModel> {

    private final DestinationsCreateCommand command;

    public DestinationsCreateUseCase(DestinationsCreateCommand command) {
        this.command = command;
    }

    @Override
    public void execute(DestinationModel input) {
        List<String> errors = ModelValidatorUtil.isValid(input);
        if (!errors.isEmpty()) {
            throw new CoreException(
                    errors,
                    getClass().getName(),
                    "Error to validate input"
            );
        }
        command.publishCreate(input);
    }
}
