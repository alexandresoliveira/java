package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FlightsControllerIndexResponse(
        List<Flight> flights
) {

    public record Flight(
            UUID id,
            String company,
            Integer flightNumber,
            String origin,
            String destiny,
            List<Seat> availableSeats,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            BigDecimal price
    ) {

        public Flight(FlightModel model) {
            this(
                    model.getId(),
                    model.getCompany(),
                    model.getFlightNumber(),
                    model.getOrigin(),
                    model.getDestiny(),
                    model.getSeats().stream().map(seatModel -> new Seat(seatModel.getId(), seatModel.getSeatNumber())).toList(),
                    model.getCheckIn(),
                    model.getCheckOut(),
                    model.getPrice());
        }
    }

    public record Seat(
            UUID id,
            String seatNumber
    ) {}
}
