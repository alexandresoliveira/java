package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayOutputDto;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTicTacToeMovementDAOFake implements GameTicTacToeMovementDAO {
  protected List<GameEntity> gameEntityList = new ArrayList<>();
  protected List<MovementEntity> movementEntityList = new ArrayList<>();

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
    return gameEntityList
      .stream()
      .filter(filter -> filter
        .getId()
        .equals(id) && filter
        .getStatus()
        .equals(GameStatus.INPROGRESS))
      .findFirst()
      .orElseThrow(() -> new GameTicTacToeMovementException("O jogo não foi encontrado"));
  }

  @Override
  public Boolean findMovementByGameAndXAndY(
    GameEntity gameEntity,
    Integer positionX,
    Integer positionY
  ) {
    return movementEntityList
      .stream()
      .anyMatch(filter ->
        isValidMovement(
          gameEntity,
          positionX,
          positionY,
          filter
        )
      );
  }

  private boolean isValidMovement(
    GameEntity gameEntity,
    Integer positionX,
    Integer positionY,
    MovementEntity filter
  ) {
    return filter
      .getGame()
      .getId()
      .equals(gameEntity.getId()) &&
      filter
        .getX()
        .equals(positionX) &&
      filter
        .getY()
        .equals(positionY);
  }

  @Override
  public void saveMovement(MovementEntity movementEntity) {
    this.movementEntityList.add(movementEntity);
  }

  @Override
  public GameTicTacToePlayOutputDto checkGameMovement(UUID gameId) {
    final AtomicInteger indexX = new AtomicInteger(0);
    final AtomicInteger indexY = new AtomicInteger(0);

    List<MovementEntity> movementEntities = movementEntityList
      .stream()
      .filter(filter -> {
        if (
            filter.getX().equals(indexX.get()) &&
            filter.getY().equals(indexY.get()) &&
            filter.getGame().getId().equals(gameId)
        ) {
          indexX.set(0);
          indexY.set(indexY.get() + 1);
          return true;
        }
        return false;
      })
      .toList();

    return new GameTicTacToePlayOutputDto(
      movementEntities.size() == 3,
      movementEntityList
        .get(0)
        .getPlayer()
    );
  }

  @Override
  public void saveGame(GameEntity entity) {
    gameEntityList
      .stream()
      .filter(filter -> filter
        .getId()
        .equals(entity.getId()))
      .findFirst()
      .ifPresentOrElse(
        gameEntity -> gameEntity = entity,
        () -> gameEntityList.add(entity)
      );
  }
}
