package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.index;

import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;

import java.util.List;

public interface TransfersIndexRepository {

    List<? extends TransferModel> findAllByModel(TransferModel model);
}
