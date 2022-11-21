package dev.alexandreoliveira.mp.registrationservice.database.jpa.repositories;

import dev.alexandreoliveira.mp.registrationservice.database.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

  Optional<UserEntity> findByEmailEquals(String email);

}
