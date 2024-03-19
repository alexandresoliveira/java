package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.flights;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.create.FlightsCreateRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class WriteFlightsRepository implements FlightsCreateRepository {

    private final JpaRepository<FlightEntity, UUID> jpaRepository;

    public WriteFlightsRepository(@Qualifier("FlightsRepository") JpaRepository<FlightEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public FlightModel save(FlightModel model) {
        return jpaRepository.save((FlightEntity) model);
    }
}
