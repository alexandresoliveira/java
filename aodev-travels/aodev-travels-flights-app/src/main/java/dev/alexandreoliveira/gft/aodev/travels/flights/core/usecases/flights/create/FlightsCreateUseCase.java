package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.create;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.ModelValidatorUtil;

import java.util.List;

public class FlightsCreateUseCase implements IUseCase<FlightModel, FlightModel> {

    private final FlightsCreateRepository repository;

    public FlightsCreateUseCase(FlightsCreateRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<FlightModel> execute(FlightModel flightModel) {
        List<String> errors = ModelValidatorUtil.isValid(flightModel);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.save(flightModel));
    }
}
