package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.index;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record HotelsIntexControllerResponse(
        List<Hotel> hotels
) {

    public record Hotel(
            UUID id,
            String name,
            String city,
            String country,
            List<Room> rooms
    ) {}

    public record Room(
            UUID id,
       String type,
       String beds,
       BigDecimal price
    ) {}
}


