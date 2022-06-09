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

public class GameTicTacToeMovementDAOFake implements GameTicTacToeMovementDAO {
  protected List<GameEntity> gameEntityList = new ArrayList<>();
  protected List<MovementEntity> movementEntityList = new ArrayList<>();

  @Override
  public void isValid(GameTicTacToeMovementInputDto input) {
    if (Objects.isNull(input)) {
      throw new GameTicTacToeMovementException("O objeto de entrada está nulo.");
    }

    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      Set<ConstraintViolation<GameTicTacToeMovementInputDto>> constraintViolations = validatorFactory.getValidator()
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
    return gameEntityList.stream()
                         .filter(filter -> filter.getId().equals(id) && filter.getStatus()
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
    return movementEntityList.stream().anyMatch(filter -> isValidMovement(
      gameEntity,
      positionX,
      positionY,
      filter
    ));
  }

  private boolean isValidMovement(
    GameEntity gameEntity,
    Integer positionX,
    Integer positionY,
    MovementEntity filter
  ) {
    return filter.getGame().getId().equals(gameEntity.getId()) && filter.getX().equals(positionX) && filter.getY()
                                                                                                           .equals(positionY);
  }

  @Override
  public void saveMovement(MovementEntity movementEntity) {
    this.movementEntityList.add(movementEntity);
  }

  @Override
  public GameTicTacToePlayOutputDto checkGameMovement(UUID gameId) {
    if (movementEntityList.size() == 9) {
      return new GameTicTacToePlayOutputDto(
        true,
        "-"
      );
    } else if (movementEntityList.size() == 3 && movementEntityList.get(0).getPlayer().equals("X")) {
      return new GameTicTacToePlayOutputDto(
        true,
        movementEntityList.get(0).getPlayer()
      );
    } else {
      return new GameTicTacToePlayOutputDto(
        false,
        ""
      );
    }
  }

  @Override
  public void saveGame(GameEntity entity) {
    gameEntityList.stream().filter(filter -> filter.getId().equals(entity.getId())).findFirst().ifPresentOrElse(
      gameEntity -> gameEntity = entity,
      () -> gameEntityList.add(entity)
    );
  }
}
