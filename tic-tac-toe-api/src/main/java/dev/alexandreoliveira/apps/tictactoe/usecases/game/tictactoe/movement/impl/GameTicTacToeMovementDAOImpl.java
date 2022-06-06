package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.MovementRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementDAO;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementException;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayOutputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component("gameTicTacToeMovementDAOImpl")
public class GameTicTacToeMovementDAOImpl implements GameTicTacToeMovementDAO {

  private final GameRepository gameRepository;
  private final MovementRepository movementRepository;
  private final GameTicTacToePlayService gameTicTacToePlayService;

  public GameTicTacToeMovementDAOImpl(
    GameRepository gameRepository,
    MovementRepository movementRepository,
    GameTicTacToePlayService gameTicTacToePlayService
  ) {
    this.gameRepository = gameRepository;
    this.movementRepository = movementRepository;
    this.gameTicTacToePlayService = gameTicTacToePlayService;
  }

  @Override
  public void isValid(GameTicTacToeMovementInputDto input) {
    if (Objects.isNull(input)) {
      throw new GameTicTacToeMovementException("O objeto de entrada está nulo.");
    }

    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      Set<ConstraintViolation<GameTicTacToeMovementInputDto>> constraintViolations = validatorFactory
        .getValidator()
        .validate(input);
      if (!constraintViolations.isEmpty()) {
        throw new ConstraintViolationException(constraintViolations);
      }
    } catch (Exception exception) {
      throw new GameTicTacToeMovementException(
        "Erro ao validar o objeto de entrada",
        exception
      );
    }
  }

  @Override
  public GameEntity findGameById(UUID id) {
    ExampleMatcher exampleMatcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

    var gameEntity = new GameEntity();
    gameEntity.setId(id);
    gameEntity.setStatus(GameStatus.INPROGRESS);

    Example<GameEntity> gameEntityExample = Example.of(
      gameEntity,
      exampleMatcher
    );

    return gameRepository
      .findOne(gameEntityExample)
      .orElseThrow(() -> new GameTicTacToeMovementException("O jogo não foi encontrado"));
  }

  @Override
  public Boolean findMovementByGameAndXAndY(
    GameEntity gameEntity,
    Integer positionX,
    Integer positionY
  ) {
    ExampleMatcher exampleMatcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

    MovementEntity movementEntity = new MovementEntity();
    movementEntity.setGame(gameEntity);
    movementEntity.setX(positionX);
    movementEntity.setY(positionY);

    Example<MovementEntity> movementEntityExample = Example.of(
      movementEntity,
      exampleMatcher
    );

    return movementRepository.exists(movementEntityExample);
  }

  @Override
  public void saveMovement(MovementEntity movementEntity) {
    movementRepository.saveAndFlush(movementEntity);
  }

  @Override
  public GameTicTacToePlayOutputDto checkGameMovement(UUID gameId) {
    var input = new GameTicTacToePlayInputDto(gameId);
    return gameTicTacToePlayService.execute(input);
  }

  @Override
  public void saveGame(GameEntity entity) {
    gameRepository.save(entity);
  }
}
