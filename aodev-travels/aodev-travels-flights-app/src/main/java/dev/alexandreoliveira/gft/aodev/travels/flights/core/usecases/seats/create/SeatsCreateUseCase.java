package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.create;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.ModelValidatorUtil;

import java.util.List;

public class SeatsCreateUseCase implements IUseCase<SeatModel, SeatModel> {

    private final SeatsCreateRepository repository;

    public SeatsCreateUseCase(SeatsCreateRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<SeatModel> execute(SeatModel seatModel) {
        List<String> errors = ModelValidatorUtil.isValid(seatModel);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.save(seatModel));
    }
}
