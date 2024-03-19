package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.flights;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.index.FlightsIndexRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReadFlightsRepository implements FlightsIndexRepository {

    private final JpaRepository<FlightEntity, UUID> jpaRepository;

    public ReadFlightsRepository(@Qualifier("FlightsRepository") JpaRepository<FlightEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<? extends FlightModel> findAllByParams(FlightModel flightModel) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var exampleFlightEntity = new FlightEntity();
        exampleFlightEntity.setOrigin(flightModel.getOrigin());
        exampleFlightEntity.setDestiny(flightModel.getDestiny());

        return jpaRepository.findAll(Example.of(exampleFlightEntity, exampleMatcher));
    }
}
