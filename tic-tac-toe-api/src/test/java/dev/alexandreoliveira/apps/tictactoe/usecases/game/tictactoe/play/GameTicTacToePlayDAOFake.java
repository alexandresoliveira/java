package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

public class GameTicTacToePlayDAOFake implements GameTicTacToePlayDAO {
  protected List<MovementEntity> movementEntityList = new ArrayList<>();

  @Override
  public void isValid(GameTicTacToePlayInputDto input) {
    if (Objects.isNull(input)) {
      throw new GameTicTacToePlayException("O objeto de entrada está nulo.");
    }

    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      Set<ConstraintViolation<GameTicTacToePlayInputDto>> constraintViolations = validatorFactory
        .getValidator()
        .validate(input);
      if (!constraintViolations.isEmpty()) {
        throw new ConstraintViolationException(constraintViolations);
      }
    } catch (Exception exception) {
      throw new GameTicTacToePlayException(
        "Erro ao validar o objeto de entrada",
        exception
      );
    }
  }

  @Override
  public List<MovementEntity> findMovementsByGameId(UUID gameId) {
    return movementEntityList
      .stream()
      .filter(filter -> filter
        .getGame()
        .getId()
        .equals(gameId))
      .collect(Collectors.toList());
  }
}
