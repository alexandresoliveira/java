package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.index.DestinationsIndexRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.DestinationEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional(transactionManager = "readTransactionManager", readOnly = true)
public class ReadDestinationsRepository implements DestinationsIndexRepository {

    private final JpaRepository<DestinationEntity, UUID> jpaRepository;

    public ReadDestinationsRepository(@Qualifier("readJpaDestinationsRepository") JpaRepository<DestinationEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<? extends DestinationModel> findAllByParams(DestinationModel model) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var exampleDestination = new DestinationEntity();
        exampleDestination.setCity(model.getCity());
        exampleDestination.setState(model.getState());

        return jpaRepository.findAll(Example.of(exampleDestination, exampleMatcher));
    }
}
