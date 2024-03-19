package dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock;

import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;

public interface TransfersLockReservationPublisher {

    void notify(TransferModel transferModel);
}
