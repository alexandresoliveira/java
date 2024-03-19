package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.hotels;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationsLocksHotelsUseCase implements IUseCase.InOut<ReservationModel, ReservationModel> {

    private final ReservationsLocksHotelsRepository repository;

    public ReservationsLocksHotelsUseCase(ReservationsLocksHotelsRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<ReservationModel> execute(ReservationModel model) {
        List<String> errors = isValidReservationModelForLockEvent(model);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        ReservationModel reservationModel = repository.lockHotelForReservation(model.getId(), model.getHotel().getExternalId());
        return new OutputDTO<>(reservationModel);
    }

    private List<String> isValidReservationModelForLockEvent(ReservationModel model) {
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(model.getId())) {
            errors.add("O identificador da reserva está nulo");
        }
        if (Objects.isNull(model.getHotel()) || Objects.isNull(model.getHotel().getExternalId())) {
            errors.add("O identificador do hotels está nulo");
        }
        return errors;
    }
}
