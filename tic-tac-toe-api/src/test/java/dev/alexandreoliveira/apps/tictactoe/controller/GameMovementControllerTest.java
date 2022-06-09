package dev.alexandreoliveira.apps.tictactoe.controller;

import dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement.GameTicTacToeMovementController;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(GameTicTacToeMovementController.class)
@TestMethodOrder(OrderAnnotation.class)
public class GameMovementControllerTest {

  //	@Autowired
  //	MockMvc mockMvc;
  //
  //	@MockBean
  //  GameMovementService gameMovementService;
  //
  //	@MockBean
  //  GameRepository gameRepository;
  //
  //	@Test
  //	@Order(1)
  //	public void shouldToSendAMovementAndReturnOk() throws Exception {
  //
  //		UUID uuid = UUID.randomUUID();
  //
  //		var position = new GameMovementPositionDto();
  //		position.setX(0);
  //		position.setY(0);
  //
  //		var request = new GameMovementRequestDto();
  //		request.setId(uuid);
  //		request.setPlayer("X");
  //		request.setPosition(position);
  //
  //		when(gameMovementService.execute(uuid, request)).thenReturn(null);
  //
  //		var content = new ObjectMapper().writeValueAsString(request);
  //
  //		// @formatter:off
  //		mockMvc
  //			.perform(post("/v1/game/{id}/movement", UUID.randomUUID())
  //				.contentType(MediaType.APPLICATION_JSON)
  //				.content(content))
  //			.andExpect(status().isOk());
  //		// @formatter:on
  //	}
  //
  //	@Test
  //	@Order(2)
  //	public void shouldToSendAMessageWhenInvalidRequest() throws Exception {
  //		UUID uuid = UUID.randomUUID();
  //
  //		var position = new GameMovementPositionDto();
  //		position.setX(5);
  //		position.setY(-4);
  //
  //		var request = new GameMovementRequestDto();
  //		request.setId(uuid);
  //		request.setPlayer("B");
  //		request.setPosition(position);
  //
  //		var content = new ObjectMapper().writeValueAsString(request);
  //
  //		when(gameMovementService.execute(uuid, request)).thenReturn(null);
  //
  //		// @formatter:off
  //		mockMvc
  //			.perform(post("/v1/game/{id}/movement", uuid)
  //				.contentType(MediaType.APPLICATION_JSON)
  //				.content(content))
  //			.andExpect(status().isBadRequest())
  //			.andExpect(jsonPath("$.['position.x']", Matchers.hasSize(1)))
  //			.andExpect(jsonPath("$.['position.y']", Matchers.hasSize(1)))
  //			.andExpect(jsonPath("$.player", Matchers.hasSize(1)));
  //		// @formatter:on
  //	}
}
