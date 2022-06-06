package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.usecases.game.play.tictactoe.GamePlayTicTacToeService")
@TestMethodOrder(OrderAnnotation.class)
public class GameTicTacToePlayServiceTest {

  static Stream<Arguments> should_check_board_when_winner_data() {
    GameEntity gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());

    List<MovementEntity> finish1 =
      Arrays.asList(
        new MovementEntity(
          gameEntityFake,
          "X",
          0,
          0
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          1,
          0
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          2,
          0
        )
      );

    List<MovementEntity> finish2 =
      Arrays.asList(
        new MovementEntity(
          gameEntityFake,
          "O",
          1,
          2
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          1,
          1
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          1,
          0
        )
      );

    List<MovementEntity> finish3 =
      Arrays.asList(
        new MovementEntity(
          gameEntityFake,
          "X",
          2,
          2
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          1,
          1
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          0,
          0
        )
      );

    List<MovementEntity> finish4 =
      Arrays.asList(
        new MovementEntity(
          gameEntityFake,
          "O",
          0,
          2
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          1,
          1
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          2,
          0
        )
      );

    return
      Stream.of(
        Arguments.of(finish1),
        Arguments.of(finish2),
        Arguments.of(finish3),
        Arguments.of(finish4)
      );
  }

  static Stream<Arguments> should_expected_exception_when_try_to_add_wrong_position_data() {
    GameEntity gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());

    MovementEntity invalid_x_1 = new MovementEntity(
      gameEntityFake,
      "X",
      -1,
      1
    );
    MovementEntity invalid_x_3 = new MovementEntity(
      gameEntityFake,
      "X",
      3,
      1
    );
    MovementEntity invalid_y_1 = new MovementEntity(
      gameEntityFake,
      "X",
      0,
      -1
    );
    MovementEntity invalid_y_3 = new MovementEntity(
      gameEntityFake,
      "X",
      2,
      3
    );

    return Stream.of(
      Arguments.of(List.of(invalid_x_1)),
      Arguments.of(List.of(invalid_x_3)),
      Arguments.of(List.of(invalid_y_1)),
      Arguments.of(List.of(invalid_y_3))
    );
  }

  @Test
  @Order(1)
  void should_expected_exception_when_input_is_null() {
    var dao = new GameTicTacToePlayDAOFake();
    var sut = new GameTicTacToePlayService(dao);

    GameTicTacToePlayException exception = assertThrows(
      GameTicTacToePlayException.class,
      () -> sut.execute(null),
      "Esperado uma excessão ao executar um método com uma entrada inválida"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("O objeto de entrada está nulo.");
  }

  @Test
  @Order(2)
  void should_expected_exception_when_input_data_is_invalid() {
    var dao = new GameTicTacToePlayDAOFake();
    var sut = new GameTicTacToePlayService(dao);

    var input = new GameTicTacToePlayInputDto(
      null
    );

    GameTicTacToePlayException exception = assertThrows(
      GameTicTacToePlayException.class,
      () -> sut.execute(input),
      "É esperado uma excessão ao enviar um objeto com atributos obrigatórios nulos"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Erro ao validar o objeto de entrada");
    assertThat(exception.getCause()).isInstanceOf(ConstraintViolationException.class);

    ConstraintViolationException exceptionCause = (ConstraintViolationException) exception.getCause();
    assertThat(exceptionCause.getConstraintViolations()).isNotEmpty();
    assertThat(exceptionCause
      .getConstraintViolations()
      .size()).isEqualTo(1);
  }

  @Test
  @Order(3)
  void should_check_board_when_zero_movements() {
    var dao = new GameTicTacToePlayDAOFake();
    var sut = new GameTicTacToePlayService(dao);

    var input = new GameTicTacToePlayInputDto(
      UUID.randomUUID()
    );

    GameTicTacToePlayOutputDto outputDto = sut.execute(input);

    assertThat(outputDto.getIsFinish()).isFalse();
    assertThat(outputDto.getWinner()).isNullOrEmpty();
  }

  @ParameterizedTest
  @MethodSource("should_check_board_when_winner_data")
  @Order(4)
  void should_check_board_when_winner(List<MovementEntity> movements) {
    var dao = new GameTicTacToePlayDAOFake();
    dao.movementEntityList.addAll(movements);

    var sut = new GameTicTacToePlayService(dao);

    var input = new GameTicTacToePlayInputDto(
      movements
        .get(0)
        .getGame()
        .getId()
    );

    GameTicTacToePlayOutputDto outputDto = sut.execute(input);
    assertThat(outputDto.getIsFinish()).isTrue();
    assertThat(outputDto.getWinner()).isNotEmpty();
  }

  @Test
  @Order(5)
  void should_check_board_draw_game() {
    GameEntity gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());

    List<MovementEntity> drawBoard =
      List.of(
        new MovementEntity(
          gameEntityFake,
          "X",
          0,
          0
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          0,
          1
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          0,
          2
        ),

        new MovementEntity(
          gameEntityFake,
          "O",
          1,
          0
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          1,
          1
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          1,
          2
        ),

        new MovementEntity(
          gameEntityFake,
          "X",
          2,
          0
        ),
        new MovementEntity(
          gameEntityFake,
          "X",
          2,
          1
        ),
        new MovementEntity(
          gameEntityFake,
          "O",
          2,
          2
        )
      );

    var dao = new GameTicTacToePlayDAOFake();
    dao.movementEntityList.addAll(drawBoard);

    var sut = new GameTicTacToePlayService(dao);

    var input = new GameTicTacToePlayInputDto(
      gameEntityFake.getId()
    );

    GameTicTacToePlayOutputDto output = sut.execute(input);

    assertThat(output.getIsFinish()).isTrue();
    assertThat(output.getWinner()).isNullOrEmpty();
  }

  @Test
  @Order(6)
  void should_check_diagonal_winners() {
    GameEntity gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());

    List<MovementEntity> diagonalMovementWinner_1 = List.of(
      new MovementEntity(
        gameEntityFake,
        "X",
        0,
        0
      ),
      new MovementEntity(
        gameEntityFake,
        "X",
        1,
        1
      ),
      new MovementEntity(
        gameEntityFake,
        "X",
        2,
        2
      )
    );

    var dao = new GameTicTacToePlayDAOFake();
    dao.movementEntityList.addAll(diagonalMovementWinner_1);

    var sut = new GameTicTacToePlayService(dao);

    var input = new GameTicTacToePlayInputDto(
      gameEntityFake.getId()
    );

    GameTicTacToePlayOutputDto outputDto_1 = sut.execute(input);

    assertThat(outputDto_1.getIsFinish()).isTrue();
    assertThat(outputDto_1.getWinner()).isNotBlank();
    assertThat(outputDto_1.getWinner()).isEqualTo("X");

    List<MovementEntity> diagonalMovementWinner_2 = List.of(
      new MovementEntity(
        gameEntityFake,
        "X",
        0,
        2
      ),
      new MovementEntity(
        gameEntityFake,
        "X",
        1,
        1
      ),
      new MovementEntity(
        gameEntityFake,
        "X",
        2,
        0
      )
    );

    dao.movementEntityList.clear();
    dao.movementEntityList.addAll(diagonalMovementWinner_2);

    GameTicTacToePlayOutputDto outputDto_2 = sut.execute(input);

    assertThat(outputDto_2.getIsFinish()).isTrue();
    assertThat(outputDto_2.getWinner()).isNotBlank();
    assertThat(outputDto_2.getWinner()).isEqualTo("X");
  }

  @ParameterizedTest
  @MethodSource("should_expected_exception_when_try_to_add_wrong_position_data")
  @Order(7)
  void should_expected_exception_when_try_to_add_wrong_position(final List<MovementEntity> movementEntityList) {
    GameEntity gameEntityFake = new GameEntity();
    gameEntityFake.setId(movementEntityList
      .get(0)
      .getGame()
      .getId());

    var dao = new GameTicTacToePlayDAOFake();
    dao.movementEntityList.addAll(movementEntityList);

    var sut = new GameTicTacToePlayService(dao);

    var input = new GameTicTacToePlayInputDto(
      gameEntityFake.getId()
    );

    Exception exception = assertThrows(
      Exception.class,
      () -> sut.execute(input),
      "É esperado uma exception ao enviar uma posição inválida de um movimento"
    );

    assertThat(exception).isNotNull();
    assertThat(exception).isInstanceOf(GameTicTacToePlayException.class);
    assertThat(exception.getMessage()).contains("Posição inválida para o movimento do player 'X' nas posições");
  }
}
