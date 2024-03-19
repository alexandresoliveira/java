package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record HotelsCreateControllerRequest(
        @NotNull @NotEmpty String name,
        @NotNull @NotEmpty String city,
        @NotNull @NotEmpty String state,
        @NotNull @NotEmpty List<Room> rooms
) {

    public record Room(
            @NotNull @NotEmpty String type,
            @NotNull @NotEmpty String beds,
            @NotNull BigDecimal price
    ) {}
}
