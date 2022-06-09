package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementOutputDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement.GameTicTacToeMovementControllerTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(GameTicTacToeMovementController.class)
class GameTicTacToeMovementControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Order(1)
  void should_expected_bad_request_when_request_data_is_invalid() throws Exception {
    UUID gameId = UUID.randomUUID();

    var request = new GameTicTacToeMovementControllerRequest();

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$").isArray())
      .andExpect(
        jsonPath(
          "$[0].field",
          Matchers.equalTo("startPlayer")
        )
      );
  }

  @TestConfiguration
  static class GameTicTacToeMovementControllerTestConfiguration {

    @Bean("gameTicTacToeMovementService")
    public TicTacToeService<GameTicTacToeMovementInputDto, GameTicTacToeMovementOutputDto> service() {
      return input -> new GameTicTacToeMovementOutputDto(
        "Partida Finalizada",
        "DONE",
        input.getPlayer()
      );
    }
  }
}
