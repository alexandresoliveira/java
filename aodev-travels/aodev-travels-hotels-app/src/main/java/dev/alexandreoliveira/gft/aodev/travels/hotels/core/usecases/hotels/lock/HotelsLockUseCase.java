package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.lock;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.ModelValidatorUtil;

import java.util.List;

public class HotelsLockUseCase implements IUseCase<HotelModel, HotelModel> {

    private final HotelsLockRepository repository;

    public HotelsLockUseCase(HotelsLockRepository repository) {
        this.repository = repository;
    }


    @Override
    public OutputDTO<HotelModel> execute(HotelModel model) {
        List<String> errors = ModelValidatorUtil.isValid(model);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        HotelModel hotelModel = repository.lockRoom(model);
        return new OutputDTO<>(hotelModel);
    }
}
