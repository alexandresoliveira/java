package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.BaseDAO;

public interface GameTicTacToeStartDAO extends BaseDAO<GameTicTacToeStartInputDto> {

  GameEntity save(GameEntity entity);
}
