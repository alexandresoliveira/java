package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartOutputDto;
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
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.start.GameTicTacToeStartControllerTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(GameTicTacToeStartController.class)
class GameTicTacToeStartControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Order(1)
  void should_expected_bad_request_when_request_data_is_invalid() throws Exception {
    var request = new GameTicTacToeStartRequest("B");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/start"))
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

  @Test
  @Order(2)
  void should_expected_right_response_object() throws Exception {
    var request = new GameTicTacToeStartRequest("X");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post(new URI("/v1/game/tic-tac-toe/start"))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(new ObjectMapper().writeValueAsBytes(request));

    mockMvc
      .perform(requestBuilder)
      .andExpect(status().isCreated())
      .andExpect(header().exists("location"))
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").isNotEmpty())
      .andExpect(jsonPath(
        "$.firstPlayer",
        Matchers.equalTo("X")
      ));
  }

  @TestConfiguration
  static class GameTicTacToeStartControllerTestConfiguration {

    @Bean
    public TicTacToeService<GameTicTacToeStartInputDto, GameTicTacToeStartOutputDto> service() {
      return input -> new GameTicTacToeStartOutputDto(
        UUID.randomUUID(),
        StringUtils.hasText(input.getStartPlayer()) ? input.getStartPlayer() : new Random().nextInt() % 2 == 0 ? "X" : "O"
      );
    }
  }
}
