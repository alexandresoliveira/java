package dev.alexandreoliveira.apps.tictactoe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.start.GameStartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.alexandreoliveira.apps.tictactoe.BaseTest;

@WebMvcTest(GameStartController.class)
public class GameStartControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameStartService gameStartService;

	@MockBean
	private GameRepository gameRepository;

	@Test
	public void shouldToStartANewGame() throws Exception {
		// @formatter:off
//		when(gameStartService.execute())
//			.thenReturn(new GameStartResponseDto(UUID.randomUUID(), "X"));
//
//		mockMvc.perform(post("/v1/game"))
//			   .andExpect(status().isCreated())
//			   .andExpect(jsonPath("$.id", Matchers.notNullValue()))
//			   .andExpect(jsonPath("$.firstPlayer", Matchers.is(Matchers.oneOf("X", "O"))));
		// @formatter:on
	}

}