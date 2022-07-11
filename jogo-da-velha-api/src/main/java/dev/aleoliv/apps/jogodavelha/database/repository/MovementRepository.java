package dev.aleoliv.apps.jogodavelha.database.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apps.jogodavelha.database.entity.GameEntity;
import dev.aleoliv.apps.jogodavelha.database.entity.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {

	List<MovementEntity> findAllByGame(GameEntity game);
	
	Optional<MovementEntity> findMovementByGameAndXAndY(GameEntity game, Integer x, Integer y);
}
