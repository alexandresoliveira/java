package dev.aleoliv.apps.jogodavelha.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.aleoliv.apps.jogodavelha.BaseTest;
import dev.aleoliv.apps.jogodavelha.database.repository.GameRepository;
import dev.aleoliv.apps.jogodavelha.dto.GameStartResponseDto;
import dev.aleoliv.apps.jogodavelha.service.GameStartService;

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
		when(gameStartService.execute())
			.thenReturn(new GameStartResponseDto(UUID.randomUUID(), "X"));

		mockMvc.perform(post("/v1/game"))
			   .andExpect(status().isCreated())
			   .andExpect(jsonPath("$.id", Matchers.notNullValue()))
			   .andExpect(jsonPath("$.firstPlayer", Matchers.is(Matchers.oneOf("X", "O"))));
		// @formatter:on
	}

}
