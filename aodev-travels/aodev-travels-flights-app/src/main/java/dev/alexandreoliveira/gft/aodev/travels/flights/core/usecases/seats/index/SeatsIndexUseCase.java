package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.groups.OnSeatsIndex;

import java.util.List;

public class SeatsIndexUseCase implements IUseCase<SeatModel, List<? extends SeatModel>> {

    private final SeatsIndexRepository repository;

    public SeatsIndexUseCase(SeatsIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<List<? extends SeatModel>> execute(SeatModel seatModel) {
        List<String> errors = ModelValidatorUtil.isValid(seatModel, OnSeatsIndex.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        List<? extends SeatModel> availableSeatsByFlight = repository.findAvailableSeatsByFlight(seatModel);
        return new OutputDTO<>(availableSeatsByFlight);
    }
}
