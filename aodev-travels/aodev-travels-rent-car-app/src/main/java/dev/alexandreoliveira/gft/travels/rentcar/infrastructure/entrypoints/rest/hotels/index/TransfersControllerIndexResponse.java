package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.index;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TransfersControllerIndexResponse(
        List<Transfer> transfers
) {

    public record Transfer(
            UUID id,
            String carInfo,
            BigDecimal price
    ) {}
}


