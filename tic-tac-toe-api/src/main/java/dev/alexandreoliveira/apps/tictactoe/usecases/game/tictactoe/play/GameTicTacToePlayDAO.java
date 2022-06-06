package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.BaseDAO;

import java.util.List;
import java.util.UUID;

public interface GameTicTacToePlayDAO extends BaseDAO<GameTicTacToePlayInputDto> {
  List<MovementEntity> findMovementsByGameId(UUID gameId);
}
