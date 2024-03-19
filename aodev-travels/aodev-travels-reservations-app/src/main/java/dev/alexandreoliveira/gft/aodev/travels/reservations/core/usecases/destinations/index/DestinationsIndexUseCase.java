package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.index;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.groups.OnDestinationsIndex;

import java.util.List;

public class DestinationsIndexUseCase implements IUseCase.InOut<DestinationModel, List<? extends DestinationModel>> {

    private final DestinationsIndexRepository repository;

    public DestinationsIndexUseCase(DestinationsIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<List<? extends DestinationModel>> execute(DestinationModel model) {
        List<String> errors = ModelValidatorUtil.isValid(model, OnDestinationsIndex.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.findAllByParams(model));
    }
}
