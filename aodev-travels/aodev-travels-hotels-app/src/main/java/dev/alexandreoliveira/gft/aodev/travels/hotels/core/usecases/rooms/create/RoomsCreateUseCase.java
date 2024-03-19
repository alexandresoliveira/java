package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.create;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnCreateRoom;

import java.util.List;

public class RoomsCreateUseCase implements IUseCase<RoomModel, RoomModel> {

    private final RoomsCreateRepository repository;

    public RoomsCreateUseCase(RoomsCreateRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<RoomModel> execute(RoomModel roomModel) {
        List<String> errors = ModelValidatorUtil.isValid(roomModel, OnCreateRoom.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.save(roomModel));
    }
}
