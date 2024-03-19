package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.services;

import dev.alexandreoliveira.gft.travels.rentcar.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.create.TransfersCreateUseCase;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.index.TransfersIndexUseCase;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock.TransfersLockReservationPublisher;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock.TransfersLockUseCase;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.entites.TransferEntity;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.repositories.TransfersRepository;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.create.TransfersControllerCreateRequest;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.create.TransfersControllerCreateResponse;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.index.TransfersControllerIndexRequest;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.index.TransfersControllerIndexResponse;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.subscriptions.rooms.lock.TransfersLockSubscriptionMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Throwable.class})
public class TransfersService extends BaseService {

    private final TransfersCreateUseCase transfersCreateUseCase;
    private final TransfersIndexUseCase transfersIndexUseCase;
    private final TransfersLockUseCase transfersLockUseCase;

    public TransfersService(TransfersRepository repository, TransfersLockReservationPublisher publisher) {
        this.transfersCreateUseCase = new TransfersCreateUseCase(repository);
        this.transfersIndexUseCase = new TransfersIndexUseCase(repository);
        this.transfersLockUseCase = new TransfersLockUseCase(repository, publisher);
    }

    public TransfersControllerCreateResponse create(TransfersControllerCreateRequest request) {
        var entity = new TransferEntity();
        entity.setCarInfo(request.carInfo());
        entity.setPrice(request.price());
        OutputDTO<TransferModel> output = transfersCreateUseCase.execute(entity);
        hasErrors(output, "Erro ao criar um novo hotels");
        return new TransfersControllerCreateResponse(output.data().getId());
    }

    public TransfersControllerIndexResponse index(TransfersControllerIndexRequest request) {
        var entity = new TransferEntity();
        entity.setCarInfo(request.term());
        OutputDTO<List<? extends TransferModel>> output = transfersIndexUseCase.execute(entity);
        hasErrors(output, "Erro ao recuperar os hoteis");
        List<TransfersControllerIndexResponse.Transfer> hotels = output.data().stream().map(model ->
            new TransfersControllerIndexResponse.Transfer(model.getId(), model.getCarInfo(), model.getPrice())
        ).toList();
        return new TransfersControllerIndexResponse(hotels);
    }

    public void lock(TransfersLockSubscriptionMessage message) {
        message.transfers().forEach(t -> {
            var entity = new TransferEntity();
            entity.setId(t);
            entity.setExternalId(message.reservationId());
            OutputDTO<TransferModel> output = transfersLockUseCase.execute(entity);
            hasErrors(output, "Error to lock transfer.");
        });
    }
}
