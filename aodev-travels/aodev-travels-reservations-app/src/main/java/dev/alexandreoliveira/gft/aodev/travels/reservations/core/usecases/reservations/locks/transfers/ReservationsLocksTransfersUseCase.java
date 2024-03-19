package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.transfers;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationsLocksTransfersUseCase implements IUseCase.InOut<ReservationModel, ReservationModel> {

    private final ReservationsLocksTransfersRepository repository;

    public ReservationsLocksTransfersUseCase(ReservationsLocksTransfersRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<ReservationModel> execute(ReservationModel model) {
        List<String> errors = isValidLockTransferReservationModel(model);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        ReservationModel reservationModel = repository.lockTransfersForReservation(model.getId(), model.getTransfers());
        return new OutputDTO<>(reservationModel);
    }

    private List<String> isValidLockTransferReservationModel(ReservationModel model) {
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(model.getId())) {
            errors.add("O identificador da reserva não foi encontrado");
        }
        if (CollectionUtils.isEmpty(model.getTransfers())) {
            errors.add("O identificador do transfer não foi encontrado");
        }
        return errors;
    }
}
