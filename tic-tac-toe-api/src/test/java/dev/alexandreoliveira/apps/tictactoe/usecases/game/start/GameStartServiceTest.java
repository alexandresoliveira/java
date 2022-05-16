package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

import org.junit.jupiter.api.*;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import javax.validation.ConstraintViolationException;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.usecases.game.start.GameStartService")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameStartServiceTest {

  @Test
  @Order(1)
  void should_expected_an_exception_when_input_is_invalid() {
    GameStartDAOInMemory dao = new GameStartDAOInMemory();
    GameStartService sut = new GameStartService(dao);

    var input = new GameStartInputDto();
    input.setStartPlayer("P");

    GameStartException exception = Assertions.assertThrows(
      GameStartException.class,
      () -> sut.execute(input),
      "Is expected an exception when execute this method"
    );

    Assertions.assertTrue(
      exception.getCause() instanceof ConstraintViolationException,
      "This is not a expected cause"
    );

    Assertions.assertFalse(
      ((ConstraintViolationException) exception.getCause()).getConstraintViolations().isEmpty(),
      "Is expected a list of violations"
    );
  }

  @Test
  @Order(2)
  void should_expected_start_player_is_equal_in_input_data() {
    GameStartDAOInMemory dao = new GameStartDAOInMemory();
    GameStartService sut = new GameStartService(dao);

    var input = new GameStartInputDto();
    input.setStartPlayer("O");

    GameStartOutputDto output = sut.execute(input);

    Assertions.assertEquals(
      "O",
      output.firstPlayer(),
      "Start player is not equals input player defined"
    );
  }
}
