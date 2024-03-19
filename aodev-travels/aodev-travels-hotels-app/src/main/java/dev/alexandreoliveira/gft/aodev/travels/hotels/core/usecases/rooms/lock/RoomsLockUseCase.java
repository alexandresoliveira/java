package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnLockRoom;

import java.util.List;

public class RoomsLockUseCase implements IUseCase<RoomModel, RoomModel> {

    private final RoomsLockRepository repository;
    private final RoomsLockReservationPublisher publisher;

    public RoomsLockUseCase(RoomsLockRepository repository, RoomsLockReservationPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public OutputDTO<RoomModel> execute(RoomModel roomModel) {
        List<String> errors = ModelValidatorUtil.isValid(roomModel, OnLockRoom.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        RoomModel unavailableRoom = repository.lock(roomModel.getId(), roomModel.getHotel().getId(), roomModel.getExternalId(), true);
        publisher.notifyForReservationLockRooms(unavailableRoom);
        return new OutputDTO<>(unavailableRoom);
    }
}
