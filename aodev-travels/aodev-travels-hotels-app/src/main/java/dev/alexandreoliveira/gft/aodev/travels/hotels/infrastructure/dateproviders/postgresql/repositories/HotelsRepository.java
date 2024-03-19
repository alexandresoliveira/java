package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.repositories;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.create.HotelsCreateRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.index.HotelsIndexRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.exceptions.DataProvidersException;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.HotelEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class HotelsRepository implements HotelsCreateRepository, HotelsIndexRepository {

    private final JpaRepository<HotelEntity, UUID> jpaRepository;

    public HotelsRepository(@Qualifier("jpaHotelsRepository") JpaRepository<HotelEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public HotelModel save(HotelModel model) {
        var entity = (HotelEntity) model;
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withIgnoreCase("name");
        var exampleEntity = new HotelEntity();
        exampleEntity.setName(entity.getName());
        List<HotelEntity> foundedHotels = jpaRepository.findAll(Example.of(exampleEntity, exampleMatcher));
        if (!foundedHotels.isEmpty()) {
            throw new DataProvidersException("Hotel found");
        }
        return jpaRepository.save(entity);
    }

    @Override
    public List<? extends HotelModel> findByHotelParams(HotelModel model) {
        var entity = (HotelEntity) model;
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase("name", "city", "state");
        return jpaRepository.findAll(Example.of(entity, exampleMatcher));
    }
}
