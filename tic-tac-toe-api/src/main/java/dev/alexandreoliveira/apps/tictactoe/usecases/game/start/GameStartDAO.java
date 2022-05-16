package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.BaseDAO;

public interface GameStartDAO extends BaseDAO<GameStartInputDto> {

  GameEntity save(GameEntity entity);
}
