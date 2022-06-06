package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementServiceTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameTicTacToeMovementServiceTest {

  @Test
  @Order(1)
  void should_expected_exception_when_input_is_null() {
    var dao = new GameTicTacToeMovementDAOFake();
    var sut = new GameTicTacToeMovementService(dao);

    GameTicTacToeMovementException exception = assertThrows(
      GameTicTacToeMovementException.class,
      () -> sut.execute(null),
      "Esperado uma excessão ao executar um método com uma entrada inválida"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("O objeto de entrada está nulo.");
  }

  @Test
  @Order(2)
  void should_expected_exception_when_input_data_is_invalid() {
    var dao = new GameTicTacToeMovementDAOFake();
    var sut = new GameTicTacToeMovementService(dao);

    var input = new GameTicTacToeMovementInputDto(
      null,
      null,
      null,
      null
    );

    GameTicTacToeMovementException exception = assertThrows(
      GameTicTacToeMovementException.class,
      () -> sut.execute(input),
      "É esperado uma excessão ao enviar um objeto com atributos obrigatórios nulos"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Erro ao validar o objeto de entrada");
    assertThat(exception.getCause()).isInstanceOf(ConstraintViolationException.class);

    ConstraintViolationException exceptionCause = (ConstraintViolationException) exception.getCause();
    assertThat(exceptionCause.getConstraintViolations()).isNotEmpty();
    assertThat(exceptionCause.getConstraintViolations().size()).isEqualTo(4);
  }

  @Test
  @Order(3)
  void should_expected_exception_when_game_not_found() {
    var dao = new GameTicTacToeMovementDAOFake();
    var sut = new GameTicTacToeMovementService(dao);

    var input = new GameTicTacToeMovementInputDto(
      UUID.randomUUID(),
      "X",
      0,
      2
    );

    GameTicTacToeMovementException exception = assertThrows(
      GameTicTacToeMovementException.class,
      () -> sut.execute(input),
      "É esperado uma excessão ao não encontrar o jogo"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("O jogo não foi encontrado");
  }

  @Test
  @Order(4)
  void should_expected_exception_when_is_not_actual_player_turn() {
    var gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());
    gameEntityFake.setActualPlayer("X");
    gameEntityFake.setStatus(GameStatus.INPROGRESS);

    var dao = new GameTicTacToeMovementDAOFake();
    dao.gameEntityList.add(gameEntityFake);

    var sut = new GameTicTacToeMovementService(dao);

    var input = new GameTicTacToeMovementInputDto(
      gameEntityFake.getId(),
      "O",
      1,
      1
    );

    GameTicTacToeMovementException exception = assertThrows(
      GameTicTacToeMovementException.class,
      () -> sut.execute(input),
      "É esperado uma excessão ao tentar executar um movimento do mesmo jogador em sequência"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Não é a sua vez de jogar!");
  }

  @Test
  @Order(5)
  void should_expected_exception_when_movement_has_registered() {
    var gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());
    gameEntityFake.setActualPlayer("X");
    gameEntityFake.setStatus(GameStatus.INPROGRESS);

    var movementEntityFake = new MovementEntity();
    movementEntityFake.setGame(gameEntityFake);
    movementEntityFake.setX(0);
    movementEntityFake.setY(0);
    movementEntityFake.setPlayer("O");
    movementEntityFake.setId(UUID.randomUUID());

    var dao = new GameTicTacToeMovementDAOFake();
    dao.gameEntityList.add(gameEntityFake);
    dao.movementEntityList.add(movementEntityFake);

    var sut = new GameTicTacToeMovementService(dao);

    var input = new GameTicTacToeMovementInputDto(
      gameEntityFake.getId(),
      "X",
      0,
      0
    );

    GameTicTacToeMovementException exception = assertThrows(
      GameTicTacToeMovementException.class,
      () -> sut.execute(input),
      "É esperado uma exception quando o movimento for inválido por já existir"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Movimento inválido: este espaço está ocupado");
  }

  @Test
  @Order(6)
  void should_expected_a_finish_game_with_winner() {
    var gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());
    gameEntityFake.setActualPlayer("O");
    gameEntityFake.setStatus(GameStatus.INPROGRESS);

    var movementEntityFake_1 = new MovementEntity();
    movementEntityFake_1.setGame(gameEntityFake);
    movementEntityFake_1.setX(0);
    movementEntityFake_1.setY(0);
    movementEntityFake_1.setPlayer("O");
    movementEntityFake_1.setId(UUID.randomUUID());

    var movementEntityFake_2 = new MovementEntity();
    movementEntityFake_2.setGame(gameEntityFake);
    movementEntityFake_2.setX(0);
    movementEntityFake_2.setY(1);
    movementEntityFake_2.setPlayer("O");
    movementEntityFake_2.setId(UUID.randomUUID());

    var dao = new GameTicTacToeMovementDAOFake();
    dao.gameEntityList.add(gameEntityFake);
    dao.movementEntityList.add(movementEntityFake_1);
    dao.movementEntityList.add(movementEntityFake_2);

    var sut = new GameTicTacToeMovementService(dao);

    var input = new GameTicTacToeMovementInputDto(
      gameEntityFake.getId(),
      "O",
      0,
      2
    );

    GameTicTacToeMovementOutputDto outputDto = sut.execute(input);

    assertThat(outputDto.getMessage()).isEqualTo("Partida finalizada");
    assertThat(outputDto.getStatus()).isEqualTo(GameStatus.DONE.name());
    assertThat(outputDto.getWinner()).isEqualTo("O");
  }

  @Test
  @Order(7)
  void should_expected_a_draw_game() {
    var gameEntityFake = new GameEntity();
    gameEntityFake.setId(UUID.randomUUID());
    gameEntityFake.setActualPlayer("O");

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
        )
      );

    var dao = new GameTicTacToeMovementDAOFake();
    dao.gameEntityList.add(gameEntityFake);
    dao.movementEntityList.addAll(drawBoard);

    var sut = new GameTicTacToeMovementService(dao);

    var input = new GameTicTacToeMovementInputDto(
      gameEntityFake.getId(),
      "O",
      2,
      2
    );

    GameTicTacToeMovementOutputDto outputDto = sut.execute(input);

    assertThat(outputDto.getMessage()).isEqualTo("Partida finalizada");
    assertThat(outputDto.getStatus()).isEqualTo(GameStatus.DONE.name());
    assertThat(outputDto.getWinner()).isEqualTo("-");
  }
}
