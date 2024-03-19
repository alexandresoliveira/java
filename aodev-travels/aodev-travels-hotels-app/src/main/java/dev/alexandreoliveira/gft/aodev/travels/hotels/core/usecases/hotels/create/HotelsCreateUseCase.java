package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.create;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnHotelsCreate;

import java.util.List;

public class HotelsCreateUseCase implements IUseCase<HotelModel, HotelModel> {

    private final HotelsCreateRepository repository;

    public HotelsCreateUseCase(HotelsCreateRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputDTO<HotelModel> execute(HotelModel hotelModel) {
        List<String> errors = ModelValidatorUtil.isValid(hotelModel, OnHotelsCreate.class);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.save(hotelModel));
    }
}
