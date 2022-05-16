package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

public class GameStartDAOInMemory implements GameStartDAO {
  protected List<GameEntity> gameEntityList = new ArrayList<>();

  @Override
  public void isValid(GameStartInputDto input) {
    try (var validationFactory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = validationFactory.getValidator();
      Set<ConstraintViolation<GameStartInputDto>> constraintViolations = validator.validate(input);
      if (!constraintViolations.isEmpty()) {
        throw new ConstraintViolationException(constraintViolations);
      }
    } catch (RuntimeException runtimeException) {
      throw new GameStartException(runtimeException);
    }
  }

  @Override
  public GameEntity save(GameEntity entity) {
    if (Objects.isNull(entity.getId())) {
      entity.setId(UUID.randomUUID());
      gameEntityList.add(entity);
      return entity;
    }

    gameEntityList.set(gameEntityList.indexOf(entity), entity);
    return entity;
  }
}
