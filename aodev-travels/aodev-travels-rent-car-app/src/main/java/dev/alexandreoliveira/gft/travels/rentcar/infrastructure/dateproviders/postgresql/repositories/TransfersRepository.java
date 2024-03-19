package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.repositories;

import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.create.TransfersCreateRepository;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.index.TransfersIndexRepository;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock.TransfersLockRepository;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.exceptions.DataProvidersException;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.entites.TransferEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransfersRepository implements TransfersCreateRepository, TransfersIndexRepository, TransfersLockRepository {

    private final JpaRepository<TransferEntity, UUID> jpaRepository;

    public TransfersRepository(@Qualifier("jpaTransfersRepository") JpaRepository<TransferEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TransferModel save(TransferModel model) {
        var entity = (TransferEntity) model;
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withIgnoreCase("name");
        var exampleEntity = new TransferEntity();
        exampleEntity.setCarInfo(entity.getCarInfo());
        List<TransferEntity> foundedHotels = jpaRepository.findAll(Example.of(exampleEntity, exampleMatcher));
        if (!foundedHotels.isEmpty()) {
            throw new DataProvidersException("Car for transfer exists.");
        }
        return jpaRepository.save(entity);
    }

    @Override
    public List<? extends TransferModel> findAllByModel(TransferModel model) {
        var entity = (TransferEntity) model;
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase("carInfo");
        return jpaRepository.findAll(Example.of(entity, exampleMatcher));
    }

    @Override
    public TransferModel lock(TransferModel transferModel) {
        Optional<TransferEntity> optionalTransferEntity = jpaRepository.findById(transferModel.getId());
        if (optionalTransferEntity.isEmpty()) {
            throw new DataProvidersException("Transfer not found!");
        }
        TransferEntity transferEntity = optionalTransferEntity.get();
        transferEntity.setExternalId(transferModel.getExternalId());
        transferEntity.setStatus(true);
        return jpaRepository.save(transferEntity);
    }
}
