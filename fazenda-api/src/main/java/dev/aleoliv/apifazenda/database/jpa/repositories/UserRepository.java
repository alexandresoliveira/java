package dev.aleoliv.apifazenda.database.jpa.repositories;

import dev.aleoliv.apifazenda.database.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  Optional<UserEntity> findByEmail(
    String email);
}
