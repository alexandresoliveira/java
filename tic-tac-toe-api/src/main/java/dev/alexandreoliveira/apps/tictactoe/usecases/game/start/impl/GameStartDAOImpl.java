package dev.alexandreoliveira.apps.tictactoe.usecases.game.start.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.start.GameStartDAO;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.start.GameStartException;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.start.GameStartInputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component("gameStartDAOImpl")
public class GameStartDAOImpl  implements GameStartDAO {
  private static final Logger LOGGER = LoggerFactory.getLogger(GameStartDAOImpl.class);

  private final GameRepository gameRepository;

  public GameStartDAOImpl(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public void isValid(GameStartInputDto input) {
    try (var validationFactory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = validationFactory.getValidator();
      Set<ConstraintViolation<GameStartInputDto>> constraintViolations = validator.validate(input);
      if (!constraintViolations.isEmpty()) {
        throw new ConstraintViolationException(constraintViolations);
      }
    } catch (RuntimeException runtimeException) {
      LOGGER.info("Error in validation");
      throw new GameStartException(runtimeException);
    }
  }

  @Override
  public GameEntity save(GameEntity entity) {
    return gameRepository.save(entity);
  }
}
