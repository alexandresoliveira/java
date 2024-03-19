package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.users;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.show.UsersShowRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ReadUsersRepository implements UsersShowRepository {

    private final JpaRepository<UserEntity, UUID> jpaRepository;

    public ReadUsersRepository(@Qualifier("readJpaUsersRepository") JpaRepository<UserEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserModel findUserById(UUID id) {
        return jpaRepository.findById(id).orElse(new UserEntity());
    }
}
