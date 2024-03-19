package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.create.SeatsCreateRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.index.SeatsIndexRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations.SeatsReservationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.exceptions.DataProvidersException;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SeatsRepository implements SeatsReservationsRepository, SeatsCreateRepository, SeatsIndexRepository {

    private final JpaRepository<FlightEntity, UUID> flightEntityUUIDJpaRepository;
    private final EntityManager entityManager;
    private final JpaRepository<SeatEntity, UUID> jpaRepository;

    public SeatsRepository(
            @Qualifier("SeatsRepository") JpaRepository<SeatEntity, UUID> jpaRepository,
            @Qualifier("FlightsRepository") JpaRepository<FlightEntity, UUID> flightEntityUUIDJpaRepository,
            JpaContext jpaContext) {
        this.jpaRepository = jpaRepository;
        this.flightEntityUUIDJpaRepository = flightEntityUUIDJpaRepository;
        this.entityManager = jpaContext.getEntityManagerByManagedType(SeatEntity.class);
    }

    @Override
    public SeatModel save(SeatModel model) {
        var entity = (SeatEntity) model;
        return jpaRepository.save(entity);
    }

    @Override
    public List<? extends SeatModel> findAvailableSeatsByFlight(SeatModel seatModel) {
        var entity = (SeatEntity) seatModel;
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths().withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
        Example<SeatEntity> seatEntityExample = Example.of(entity, exampleMatcher);
        return jpaRepository.findAll(seatEntityExample);
    }

    @Override
    public SeatModel reservationSeats(SeatModel seat) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues();

        var exampleSeatEntity = new SeatEntity();
        exampleSeatEntity.setFlight(seat.getFlight());
        exampleSeatEntity.setId(seat.getId());

        Optional<SeatEntity> optionalSeat = jpaRepository.findOne(Example.of(exampleSeatEntity, exampleMatcher));
        if (optionalSeat.isEmpty()) {
            throw new DataProvidersException(String.format("Seat (%s) not found.", seat.getId()));
        }

        SeatEntity seatEntity = optionalSeat.get();

        boolean isValidToReserve = Objects.isNull(seatEntity.getExternalId());

        if (!isValidToReserve) {
            throw new DataProvidersException("Error to reserve a seat. This seat has reserved.");
        }

        seatEntity.setExternalId(seat.getExternalId());
        return jpaRepository.save(seatEntity);
    }
}
