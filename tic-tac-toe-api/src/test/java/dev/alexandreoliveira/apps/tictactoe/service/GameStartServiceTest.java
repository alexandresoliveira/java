package dev.alexandreoliveira.apps.tictactoe.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.alexandreoliveira.apps.tictactoe.IntegratedBaseTest;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.start.GameStartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
//		var gameEntity = new GameEntity();
//		gameEntity.setActualPlayer("X");
//		gameEntity.setWinner("-");
//
//		var gameReturnEntity = new GameEntity();
//		gameReturnEntity.setId(UUID.randomUUID());
//		gameReturnEntity.setActualPlayer("X");
//
//		when(gameRepository.save(gameEntity)).thenReturn(gameReturnEntity);
//
//		GameStartResponseDto responseDto = gameStartService.execute();
//
//		assertThat(responseDto.getId()).matches(uuid -> {
//			try {
//				UUID.fromString(uuid.toString());
//				return true;
//			} catch (Exception e) {
//				return false;
//			}
//		});
//
//		assertThat(responseDto.getFirstPlayer()).isEqualTo("X");
	}
}
