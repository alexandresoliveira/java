package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators.groups.OnSeatReservation;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SeatsReservationsUseCase implements IUseCase<List<? extends SeatModel>, List<SeatModel>> {

    private final SeatsReservationsRepository repository;
    private final SeatsReservationsPublisher publisher;

    public SeatsReservationsUseCase(SeatsReservationsRepository repository, SeatsReservationsPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public OutputDTO<List<SeatModel>> execute(List<? extends SeatModel> seats) {
        List<String> errors = seats.stream().map(seat ->
                        ModelValidatorUtil.isValid(seat, OnSeatReservation.class)
                ).flatMap(Collection::stream)
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }

        List<SeatModel> reservedSeats = seats.stream().map(seat -> {
            try {
                return repository.reservationSeats(seat);
            } catch (RuntimeException e) {
                errors.add(e.getMessage());
            }
            return null;
        }).filter(Objects::nonNull).toList();

        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }

        publisher.publishLockSeatEvents(reservedSeats.getFirst().getExternalId(), reservedSeats);
        return new OutputDTO<>(reservedSeats);
    }
}
