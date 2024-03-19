package dev.alexandreoliveira.gft.aodev.travels.hotels.core.models;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnCreateRoom;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnLockRoom;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.utils.validators.groups.OnUnlockRoom;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public interface RoomModel extends BaseModel {

    @NotNull(groups = {OnLockRoom.class, OnUnlockRoom.class})
    HotelModel getHotel();

    @NotNull(groups = {OnLockRoom.class, OnUnlockRoom.class})
    UUID getExternalId();

    @NotNull(groups = {OnCreateRoom.class})
    @NotEmpty(groups = {OnCreateRoom.class})
    String getType();

    @NotNull(groups = {OnCreateRoom.class})
    @NotEmpty(groups = {OnCreateRoom.class})
    String getBeds();

    Boolean isAvailable();

    BigDecimal getPrice();
}
