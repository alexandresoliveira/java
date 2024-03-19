package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.unlock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnUnlockRoom;

import java.util.List;

public class RoomsUnlockUseCase implements IUseCase<RoomModel, RoomModel> {

    private final RoomsUnlockRepository repository;

    public RoomsUnlockUseCase(RoomsUnlockRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<RoomModel> execute(RoomModel roomModel) {
        List<String> errors = ModelValidatorUtil.isValid(roomModel, OnUnlockRoom.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        RoomModel availableRoom = repository.unlockRoom(roomModel.getId(), roomModel.getHotel().getId(), roomModel.getExternalId(), true);
        return new OutputDTO<>(availableRoom);
    }
}
