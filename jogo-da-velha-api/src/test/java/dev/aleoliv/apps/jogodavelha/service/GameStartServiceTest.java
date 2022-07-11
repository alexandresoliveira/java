package dev.aleoliv.apps.jogodavelha.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.aleoliv.apps.jogodavelha.IntegratedBaseTest;
import dev.aleoliv.apps.jogodavelha.database.entity.GameEntity;
import dev.aleoliv.apps.jogodavelha.database.repository.GameRepository;
import dev.aleoliv.apps.jogodavelha.dto.GameStartResponseDto;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameStartServiceTest extends IntegratedBaseTest {

	@MockBean
	GameRepository gameRepository;

	@Autowired
	GameStartService gameStartService;

	@BeforeEach
	public void beforeEach() {
		gameRepository.deleteAll();
	}

	@Test
	@Order(1)
	public void shouldStartNewGame() {
		var gameEntity = new GameEntity();
		gameEntity.setActualPlayer("X");
		gameEntity.setWinner("-");

		var gameReturnEntity = new GameEntity();
		gameReturnEntity.setId(UUID.randomUUID());
		gameReturnEntity.setActualPlayer("X");

		when(gameRepository.save(gameEntity)).thenReturn(gameReturnEntity);

		GameStartResponseDto responseDto = gameStartService.execute();

		assertThat(responseDto.getId()).matches(uuid -> {
			try {
				UUID.fromString(uuid.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		});

		assertThat(responseDto.getFirstPlayer()).isEqualTo("X");
	}
}
