package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
}
