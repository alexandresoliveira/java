package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock;

import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;

public interface TransfersLockRepository {

    TransferModel lock(TransferModel hotelModel);
}
