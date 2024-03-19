package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.flights;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationsLocksFlightsUseCase implements IUseCase.InOut<ReservationModel, ReservationModel> {

    private final ReservationsLocksFlightsRepository repository;

    public ReservationsLocksFlightsUseCase(ReservationsLocksFlightsRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<ReservationModel> execute(ReservationModel model) {
        List<String> errors = isValidReservationFlightLockModel(model);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        ReservationModel reservationModel = repository.lockFlightsForReservation(model.getId(), model.getFlights());
        return new OutputDTO<>(reservationModel);
    }

    private List<String> isValidReservationFlightLockModel(ReservationModel model) {
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(model.getId())) {
            errors.add("O identificador da reserva não foi encontrado");
        }
        if (CollectionUtils.isEmpty(model.getFlights())) {
            errors.add("Os identificadores dos voos não foram encontrados");
        }
        return errors;
    }
}
