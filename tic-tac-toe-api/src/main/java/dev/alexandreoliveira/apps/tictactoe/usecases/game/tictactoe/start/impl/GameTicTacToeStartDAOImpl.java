package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartDAO;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartException;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartInputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component("gameTicTacToeStartDAOImpl")
public class GameTicTacToeStartDAOImpl implements GameTicTacToeStartDAO {
  private static final Logger LOGGER = LoggerFactory.getLogger(GameTicTacToeStartDAOImpl.class);

  private final GameRepository gameRepository;

  public GameTicTacToeStartDAOImpl(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public void isValid(GameTicTacToeStartInputDto input) {
    try (var validationFactory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = validationFactory.getValidator();
      Set<ConstraintViolation<GameTicTacToeStartInputDto>> constraintViolations = validator.validate(input);
      if (!constraintViolations.isEmpty()) {
        throw new ConstraintViolationException(constraintViolations);
      }
    } catch (RuntimeException runtimeException) {
      LOGGER.info("Error in validation");
      throw new GameTicTacToeStartException(runtimeException);
    }
  }

  @Override
  public GameEntity save(GameEntity entity) {
    return gameRepository.save(entity);
  }
}
