package dev.alexandreoliveira.gft.travels.rentcar.core.models;

import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.groups.OnTransfersIndex;
import dev.alexandreoliveira.gft.travels.rentcar.core.utils.validators.groups.OnTransfersLock;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferModel extends BaseModel {

    @NotNull(groups = {OnTransfersLock.class})
    UUID getExternalId();

    @NotNull(groups = {OnTransfersIndex.class})
    @NotEmpty(groups = {OnTransfersIndex.class})
    String getCarInfo();

    Boolean getStatus();

    @NotNull
    BigDecimal getPrice();
}
