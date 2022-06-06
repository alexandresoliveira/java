package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {
}
