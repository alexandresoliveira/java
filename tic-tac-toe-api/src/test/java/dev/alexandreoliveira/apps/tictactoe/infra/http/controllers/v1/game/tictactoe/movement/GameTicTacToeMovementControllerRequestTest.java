package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement.GameTicTacToeMovementControllerRequestTest")
@JsonTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameTicTacToeMovementControllerRequestTest {

  @Autowired
  private JacksonTester<GameTicTacToeMovementControllerRequest> jacksonTester;

  @Test
  @Order(1)
  void should_to_convert_to_right_object() throws IOException {
    var request = new GameTicTacToeMovementControllerRequest(
      "X",
      1,
      1
    );

    JsonContent<GameTicTacToeMovementControllerRequest> content = jacksonTester.write(request);

    assertThat(content).extractingJsonPathStringValue("$.player").isEqualTo("X");
    assertThat(content).extractingJsonPathNumberValue("$.positionX").isEqualTo(1);
    assertThat(content).extractingJsonPathNumberValue("$.positionY").isEqualTo(1);
  }
}
