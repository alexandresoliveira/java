package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
public class GameTicTacToeStartService implements TicTacToeService<GameTicTacToeStartInputDto, GameTicTacToeStartOutputDto> {
  private final GameTicTacToeStartDAO gameTicTacToeStartDAO;

  public GameTicTacToeStartService(
    @Qualifier("gameTicTacToeStartDAOImpl")
    GameTicTacToeStartDAO gameTicTacToeStartDAO
  ) {
    this.gameTicTacToeStartDAO = gameTicTacToeStartDAO;
  }

  @Override
  public GameTicTacToeStartOutputDto execute(GameTicTacToeStartInputDto input) {
    gameTicTacToeStartDAO.isValid(input);

    String firstPlayer = StringUtils.hasText(input.getStartPlayer()) ? input.getStartPlayer() : new Random().nextInt() % 2 == 0 ? "X" : "O";

    var gameEntity = new GameEntity();
    gameEntity.setActualPlayer(firstPlayer);
    gameEntity.setWinner("-");

    GameEntity savedEntity = gameTicTacToeStartDAO.save(gameEntity);

    return new GameTicTacToeStartOutputDto(
      savedEntity.getId(),
      firstPlayer
    );
  }
}
