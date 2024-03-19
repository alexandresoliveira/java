package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock;

import dev.alexandreoliveira.gft.travels.rentcar.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.groups.OnTransfersLock;

import java.util.List;

public class TransfersLockUseCase implements IUseCase<TransferModel, TransferModel> {

    private final TransfersLockRepository repository;
    private final TransfersLockReservationPublisher publisher;

    public TransfersLockUseCase(TransfersLockRepository repository, TransfersLockReservationPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public OutputDTO<TransferModel> execute(TransferModel model) {
        List<String> errors = ModelValidatorUtil.isValid(model, OnTransfersLock.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        TransferModel transferModel = repository.lock(model);
        publisher.notify(transferModel);
        return new OutputDTO<>(transferModel);
    }
}
