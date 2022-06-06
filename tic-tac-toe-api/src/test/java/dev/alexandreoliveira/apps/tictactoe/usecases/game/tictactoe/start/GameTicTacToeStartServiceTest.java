package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start;

import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolationException;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartService")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameTicTacToeStartServiceTest {

  @Test
  @Order(1)
  void should_expected_an_exception_when_input_is_invalid() {
    GameTicTacToeStartDAOInMemory dao = new GameTicTacToeStartDAOInMemory();
    GameTicTacToeStartService sut = new GameTicTacToeStartService(dao);

    var input = new GameTicTacToeStartInputDto();
    input.setStartPlayer("P");

    GameTicTacToeStartException exception = Assertions.assertThrows(
      GameTicTacToeStartException.class,
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
    GameTicTacToeStartDAOInMemory dao = new GameTicTacToeStartDAOInMemory();
    GameTicTacToeStartService sut = new GameTicTacToeStartService(dao);

    var input = new GameTicTacToeStartInputDto();
    input.setStartPlayer("O");

    GameTicTacToeStartOutputDto output = sut.execute(input);

    Assertions.assertEquals(
      "O",
      output.firstPlayer(),
      "Start player is not equals input player defined"
    );
  }
}
