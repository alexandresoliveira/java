package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.users;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.create.UsersCreateRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.exceptions.DataProvidersException;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class WriteUsersRepository implements UsersCreateRepository {

    private final JpaRepository<UserEntity, UUID> jpaRepository;

    public WriteUsersRepository(@Qualifier("writeJpaUsersRepository") JpaRepository<UserEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserModel save(UserModel model) {
        var entity = (UserEntity) model;
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase("name", "email");
        List<UserEntity> foundUsers = jpaRepository.findAll(Example.of(entity, exampleMatcher));
        if (!foundUsers.isEmpty()) {
            throw new DataProvidersException("User found. Please, create with other parameters.");
        }
        return jpaRepository.save(entity);
    }
}
