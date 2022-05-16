package dev.alexandreoliveira.apps.tictactoe.database.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.database.entity.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {

	List<MovementEntity> findAllByGame(GameEntity game);
	
	Optional<MovementEntity> findMovementByGameAndXAndY(GameEntity game, Integer x, Integer y);
}
