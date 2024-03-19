package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record TransfersControllerCreateRequest(
        @NotNull @NotEmpty String carInfo,
        @NotNull BigDecimal price
) {}
