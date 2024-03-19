package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.create;

import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;

public interface TransfersCreateRepository {

    TransferModel save(TransferModel transferModel);
}
