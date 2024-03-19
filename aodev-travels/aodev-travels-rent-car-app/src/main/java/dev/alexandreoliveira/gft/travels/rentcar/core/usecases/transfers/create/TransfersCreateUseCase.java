package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.create;

import dev.alexandreoliveira.gft.travels.rentcar.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.groups.OnTransfersCreate;

import java.util.List;

public class TransfersCreateUseCase implements IUseCase<TransferModel, TransferModel> {

    private final TransfersCreateRepository repository;

    public TransfersCreateUseCase(TransfersCreateRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<TransferModel> execute(TransferModel transferModel) {
        List<String> errors = ModelValidatorUtil.isValid(transferModel, OnTransfersCreate.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.save(transferModel));
    }
}
