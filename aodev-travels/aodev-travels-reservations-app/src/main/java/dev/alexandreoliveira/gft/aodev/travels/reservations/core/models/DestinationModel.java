package dev.alexandreoliveira.gft.aodev.travels.reservations.core.models;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.groups.OnDestinationsIndex;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

public interface DestinationModel extends BaseModel {

    @NotNull(groups = {Default.class, OnDestinationsIndex.class})
    @NotEmpty(groups = {Default.class, OnDestinationsIndex.class})
    @Size(min = 3, groups = {Default.class, OnDestinationsIndex.class})
    String getCity();

    @NotNull(groups = {Default.class, OnDestinationsIndex.class})
    @NotEmpty(groups = {Default.class, OnDestinationsIndex.class})
    @Size(min = 3, groups = {Default.class, OnDestinationsIndex.class})
    String getState();
}
