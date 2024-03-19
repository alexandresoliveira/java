package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.show;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;

public class ReservationsShowUseCase implements IUseCase.InOut<ReservationModel, ReservationModel> {

    private final ReservationsShowRepository repository;

    public ReservationsShowUseCase(ReservationsShowRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<ReservationModel> execute(ReservationModel reservationModel) {
        return new OutputDTO<>(repository.findById(reservationModel));
    }
}
