package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayOutputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("gameTicTacToeMovementService")
public class GameTicTacToeMovementService implements TicTacToeService<GameTicTacToeMovementInputDto, GameTicTacToeMovementOutputDto> {
  private static final Logger LOGGER = LoggerFactory.getLogger(GameTicTacToeMovementService.class);
  private static final String GAME_FINISH = "Partida finalizada";

  private final GameTicTacToeMovementDAO dao;

  public GameTicTacToeMovementService(
    @Qualifier("gameTicTacToeMovementDAOImpl")
    GameTicTacToeMovementDAO dao
  ) {
    this.dao = dao;
  }

  @Override
  public GameTicTacToeMovementOutputDto execute(GameTicTacToeMovementInputDto input) {
    dao.isValid(input);

    GameEntity game = dao.findGameById(input.getGameId());

    if (!game
      .getActualPlayer()
      .equalsIgnoreCase(input.getPlayer())) {
      throw new GameTicTacToeMovementException("Não é a sua vez de jogar!");
    }

    if (dao.findMovementByGameAndXAndY(
      game,
      input.getPositionX(),
      input.getPositionY()
    )) {
      throw new GameTicTacToeMovementException("Movimento inválido: este espaço está ocupado");
    }

    var actualMovement = new MovementEntity(
      game,
      input.getPlayer(),
      input.getPositionX(),
      input.getPositionY()
    );
    dao.saveMovement(actualMovement);

    GameTicTacToePlayOutputDto outputDto = dao.checkGameMovement(game.getId());

    if (outputDto.getIsFinish() && StringUtils.hasText(outputDto.getWinner())) {
      game.setWinner(outputDto.getWinner());
      game.setStatus(GameStatus.DONE);
      dao.saveGame(game);

      return new GameTicTacToeMovementOutputDto(
        "Partida finalizada",
        game
          .getStatus()
          .name(),
        game.getWinner()
      );
    }

    if (outputDto.getIsFinish()) {
      game.setWinner("-");
      game.setStatus(GameStatus.DONE);
      dao.saveGame(game);

      return new GameTicTacToeMovementOutputDto(
        "Partida finalizada",
        game
          .getStatus()
          .name(),
        game.getWinner()
      );
    }

    game.setStatus(GameStatus.INPROGRESS);
    game.setActualPlayer(input
      .getPlayer()
      .equalsIgnoreCase("X") ? "O" : "X");
    dao.saveGame(game);

    return new GameTicTacToeMovementOutputDto(
      "Em andamento",
      GameStatus.INPROGRESS.name(),
      ""
    );
  }
}
