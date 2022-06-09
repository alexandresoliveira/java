package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {
}
