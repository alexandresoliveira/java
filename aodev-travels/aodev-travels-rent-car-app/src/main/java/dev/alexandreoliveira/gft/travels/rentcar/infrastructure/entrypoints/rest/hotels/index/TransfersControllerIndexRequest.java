package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.index;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransfersControllerIndexRequest(
        @NotNull @NotEmpty @Size(min = 3) String term
) {
}
