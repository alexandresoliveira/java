package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
public class GameStartService implements TicTacToeService<GameStartInputDto, GameStartOutputDto> {
	private final GameStartDAO gameStartDAO;

	public GameStartService(
    @Qualifier("gameStartDAOImpl") GameStartDAO gameStartDAO
  ) {
		this.gameStartDAO = gameStartDAO;
	}

  @Override
  public GameStartOutputDto execute(GameStartInputDto input) {
    gameStartDAO.isValid(input);

    String firstPlayer =
      StringUtils.hasText(input.getStartPlayer()) ?
        input.getStartPlayer() :
        new Random().nextInt() % 2 == 0 ? "X" : "O";

    var gameEntity = new GameEntity();
    gameEntity.setActualPlayer(firstPlayer);
    gameEntity.setWinner("-");

    GameEntity savedEntity = gameStartDAO.save(gameEntity);

    return new GameStartOutputDto(savedEntity.getId(), firstPlayer);
  }
}
