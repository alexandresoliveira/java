package dev.aleoliv.apifazenda.database.jpa.repositories;

import dev.aleoliv.apifazenda.database.jpa.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, UUID> {

  Optional<AuthorityEntity> findByName(
    String name
  );
}
