package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {

  @Query(
    value = "select * from movements m where m.game_id = :gameId and m.created_at > :createdAt",
    nativeQuery = true
  )
  List<MovementEntity> findAllMovementsByGameIdAndCreatedDateAfterParam(
    @Param("gameId") UUID gameId,
    @Param("createdAt") LocalDateTime createdAt
  );
}
