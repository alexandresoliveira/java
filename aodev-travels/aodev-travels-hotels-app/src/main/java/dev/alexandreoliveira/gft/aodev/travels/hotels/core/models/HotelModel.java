package dev.alexandreoliveira.gft.aodev.travels.hotels.core.models;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnHotelsCreate;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnHotelsIndex;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface HotelModel extends BaseModel {

    @NotNull(groups = {OnHotelsIndex.class, OnHotelsCreate.class})
    @NotEmpty(groups = {OnHotelsCreate.class, OnHotelsIndex.class})
    String getName();

    @NotNull(groups = {OnHotelsIndex.class, OnHotelsCreate.class})
    @NotEmpty(groups = {OnHotelsCreate.class, OnHotelsIndex.class})
    String getCity();

    @NotNull(groups = {OnHotelsIndex.class, OnHotelsCreate.class})
    @NotEmpty(groups = {OnHotelsCreate.class, OnHotelsIndex.class})
    String getState();

    List<? extends RoomModel> getRooms();
}
