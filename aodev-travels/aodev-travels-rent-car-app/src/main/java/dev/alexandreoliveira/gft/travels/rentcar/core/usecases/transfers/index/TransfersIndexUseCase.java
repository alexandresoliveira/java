package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.index;


import dev.alexandreoliveira.gft.travels.rentcar.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.groups.OnTransfersIndex;

import java.util.List;

public class TransfersIndexUseCase implements IUseCase<TransferModel, List<? extends TransferModel>> {

    private final TransfersIndexRepository repository;

    public TransfersIndexUseCase(TransfersIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<List<? extends TransferModel>> execute(TransferModel model) {
        List<String> errors = ModelValidatorUtil.isValid(model, OnTransfersIndex.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.findAllByModel(model));
    }
}
