package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.groups.OnFlightsIndex;

import java.util.List;

public class FlightsIndexUseCase implements IUseCase<FlightModel, List<? extends FlightModel>> {

    private final FlightsIndexRepository repository;

    public FlightsIndexUseCase(FlightsIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<List<? extends FlightModel>> execute(FlightModel flightModel) {
        List<String> errors = ModelValidatorUtil.isValid(flightModel, OnFlightsIndex.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.findAllByParams(flightModel));
    }
}
