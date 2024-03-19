package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FlightsControllerIndexRequest(
        @NotNull @NotEmpty String cityOrigin,
        @NotNull @NotEmpty String cityDestiny
) {
}
