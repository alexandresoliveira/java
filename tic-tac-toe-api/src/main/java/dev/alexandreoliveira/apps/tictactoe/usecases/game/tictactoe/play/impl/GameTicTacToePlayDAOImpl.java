package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.MovementRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayDAO;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayException;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.GameTicTacToePlayInputDto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component("gameTicTacToePlayDAOImpl")
public class GameTicTacToePlayDAOImpl implements GameTicTacToePlayDAO {
  private final MovementRepository movementRepository;

  public GameTicTacToePlayDAOImpl(
    MovementRepository movementRepository
  ) {
    this.movementRepository = movementRepository;
  }

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
    ExampleMatcher exampleMatcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

    GameEntity gameEntity = new GameEntity();
    gameEntity.setId(gameId);

    MovementEntity movementEntity = new MovementEntity();
    movementEntity.setGame(gameEntity);

    Example<MovementEntity> movementEntityExample = Example.of(
      movementEntity,
      exampleMatcher
    );

    return movementRepository.findAll(movementEntityExample);
  }
}
