package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementOutputDto;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement.GameTicTacToeMovementControllerTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(GameTicTacToeMovementController.class)
class GameTicTacToeMovementControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Order(1)
  void should_expected_bad_request_when_request_with_invalid_uuid() throws Exception {
    var request = new GameTicTacToeMovementControllerRequest(
      null,
      null,
      null
    );

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/invalid-uuid/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest())
      .andDo(print());
  }

  @Test
  @Order(2)
  void should_expected_bad_request_when_request_with_no_data() throws Exception {
    UUID gameId = UUID.randomUUID();

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(null));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  @Order(3)
  void should_expected_bad_request_when_request_data_is_invalid() throws Exception {
    UUID gameId = UUID.randomUUID();

    var request = new GameTicTacToeMovementControllerRequest(
      null,
      null,
      null
    );

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  @Order(4)
  void should_expected_bad_request_when_request_data_has_invalid_player() throws Exception {
    UUID gameId = UUID.randomUUID();

    var request = new GameTicTacToeMovementControllerRequest(
      "H",
      1,
      1
    );

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  @Order(5)
  void should_expected_bad_request_when_request_data_has_invalid_x_position() throws Exception {
    UUID gameId = UUID.randomUUID();

    var request = new GameTicTacToeMovementControllerRequest(
      "X",
      -5,
      1
    );

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  @Order(6)
  void should_expected_bad_request_when_request_data_has_invalid_y_position() throws Exception {
    UUID gameId = UUID.randomUUID();

    var request = new GameTicTacToeMovementControllerRequest(
      "X",
      0,
      8
    );

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  @Order(7)
  void should_expected_success_response_data() throws Exception {
    UUID gameId = UUID.randomUUID();

    var request = new GameTicTacToeMovementControllerRequest(
      "X",
      0,
      1
    );

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/" + gameId + "/movement"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isCreated())
      .andExpect(header().exists("location"))
      .andExpect(jsonPath(
        "$",
        hasKey("message")
      ))
      .andExpect(jsonPath(
        "$",
        hasKey("winner")
      ))
      .andExpect(jsonPath(
        "$",
        hasKey("status")
      ));
  }

  @TestConfiguration
  static class GameTicTacToeMovementControllerTestConfiguration {

    @Bean("gameTicTacToeMovementService")
    public TicTacToeService<GameTicTacToeMovementInputDto, GameTicTacToeMovementOutputDto> service() {
      return input -> new GameTicTacToeMovementOutputDto(
        "Partida Finalizada",
        "DONE",
        input.player()
      );
    }
  }
}
