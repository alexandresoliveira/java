package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.create.DestinationsCreateRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.update.DestinationsUpdateRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.exceptions.DataProvidersException;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.DestinationEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class WriteDestinationsRepository implements DestinationsCreateRepository, DestinationsUpdateRepository {

    private final JpaRepository<DestinationEntity, UUID> jpaRepository;
    private final TransactionTemplate transactionTemplate;

    public WriteDestinationsRepository(
            @Qualifier("writeJpaDestinationsRepository") JpaRepository<DestinationEntity, UUID> jpaRepository,
            @Qualifier("writeTransactionManager") PlatformTransactionManager platformTransactionManager) {
        this.jpaRepository = jpaRepository;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public DestinationModel save(final DestinationModel model) {
        return jpaRepository.save((DestinationEntity) model);
    }

    @Override
    public DestinationModel update(DestinationModel model) {
        Optional<DestinationEntity> optionalDestination = jpaRepository.findById(model.getId());

        if (optionalDestination.isPresent()) {
            DestinationEntity entity = optionalDestination.get();
            entity.setCity(StringUtils.hasText(model.getCity()) ? model.getCity() : entity.getCity());
            entity.setState(StringUtils.hasText(model.getState())? model.getState() : entity.getState());
            return jpaRepository.save(entity);
        }

        throw new DataProvidersException("Destination not found");
    }
}
