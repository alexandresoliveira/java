package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.BaseDAO;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayOutputDto;

import java.util.List;
import java.util.UUID;

public interface GameTicTacToeMovementDAO extends BaseDAO<GameTicTacToeMovementInputDto> {
  GameEntity findGameById(UUID id);

  Boolean findMovementByGameAndXAndY(
    GameEntity gameEntity,
    Integer positionX,
    Integer positionY
  );

  void saveMovement(MovementEntity movementEntity);

  GameTicTacToePlayOutputDto checkGameMovement(UUID gameId);

  void saveGame(GameEntity entity);
}
