package dev.alexandreoliveira.apps.tictactoe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.alexandreoliveira.apps.tictactoe.IntegratedBaseTest;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.database.entity.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.database.entity.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.database.repository.MovementRepository;
import dev.alexandreoliveira.apps.tictactoe.dto.GameMovementPositionDto;
import dev.alexandreoliveira.apps.tictactoe.dto.GameMovementRequestDto;
import dev.alexandreoliveira.apps.tictactoe.dto.GameMovementResponseDto;
import dev.alexandreoliveira.apps.tictactoe.service.exception.GameMovementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameMovementServiceTest extends IntegratedBaseTest {

	@MockBean
  GameRepository gameRepository;

	@MockBean
  MovementRepository movementRepository;

	@Autowired
	GameMovementService gameMovementService;

	@AfterEach
	public void afterEach() {
		gameRepository.deleteAll();
		movementRepository.deleteAll();
	}

	@Test
	@Order(1)
	public void shouldGameNotFound() {
		UUID id = UUID.randomUUID();

		when(gameRepository.findById(id)).thenReturn(Optional.empty());

		GameMovementException exception = Assertions.assertThrows(GameMovementException.class,
				() -> gameMovementService.execute(id, null));

		assertThat(exception.getMessage()).isEqualTo("Partida não encontrada");
	}

	@Test
	@Order(2)
	public void shouldReturnGameFinished() throws GameMovementException {
		UUID id = UUID.randomUUID();

		var gameReturn = new GameEntity();
		gameReturn.setId(id);
		gameReturn.setStatus(GameStatus.DONE);
		gameReturn.setWinner("X");

		when(gameRepository.findById(id)).thenReturn(Optional.of(gameReturn));

		GameMovementResponseDto responseDto = gameMovementService.execute(id, null);

		assertThat(responseDto.getStatus()).isEqualTo("Partida Finalizada");
		assertThat(responseDto.getWinner()).isEqualTo("X");
	}

	@Test
	@Order(3)
	public void shouldToReturnExceptionWhenIsNotTurnOfPlayer() {
		UUID id = UUID.randomUUID();

		var gameReturn = new GameEntity();
		gameReturn.setId(id);
		gameReturn.setStatus(GameStatus.INPROGRESS);
		gameReturn.setActualPlayer("X");

		var requestDto = new GameMovementRequestDto();
		requestDto.setPlayer("O");

		when(gameRepository.findById(id)).thenReturn(Optional.of(gameReturn));

		GameMovementException exception = Assertions.assertThrows(GameMovementException.class,
				() -> gameMovementService.execute(id, requestDto));

		assertThat(exception.getMessage()).isEqualTo("Não é o turno do jogador");
	}

	@Test
	@Order(4)
	public void shouldToThrowExceptionWhenPositionInBoardIsNotEmpty() {
		UUID id = UUID.randomUUID();

		var gameReturn = new GameEntity();
		gameReturn.setId(id);
		gameReturn.setStatus(GameStatus.INPROGRESS);
		gameReturn.setActualPlayer("X");

		var movementReturn = new MovementEntity();
		movementReturn.setId(UUID.randomUUID());
		movementReturn.setGame(gameReturn);
		movementReturn.setX(0);
		movementReturn.setY(1);
		movementReturn.setPlayer("O");

		var position = new GameMovementPositionDto();
		position.setX(0);
		position.setY(1);

		var requestDto = new GameMovementRequestDto();
		requestDto.setPlayer("X");
		requestDto.setPosition(position);

		when(gameRepository.findById(id)).thenReturn(Optional.of(gameReturn));
		when(movementRepository.findMovementByGameAndXAndY(gameReturn, 0, 1)).thenReturn(Optional.of(movementReturn));

		GameMovementException exception = Assertions.assertThrows(GameMovementException.class,
				() -> gameMovementService.execute(id, requestDto));

		assertThat(exception.getMessage()).isEqualTo("Movimento inválido, x=%d, y=%d, player=%s", 0, 1, "O");
	}

	@Test
	@Order(5)
	public void shouldToReturnFinishedGameWithWinner() throws GameMovementException {
		UUID id = UUID.randomUUID();

		var gameReturn = new GameEntity();
		gameReturn.setId(id);
		gameReturn.setStatus(GameStatus.INPROGRESS);
		gameReturn.setActualPlayer("X");

		var position = new GameMovementPositionDto();
		position.setX(0);
		position.setY(0);

		var requestDto = new GameMovementRequestDto();
		requestDto.setPlayer("X");
		requestDto.setPosition(position);

		when(gameRepository.findById(id)).thenReturn(Optional.of(gameReturn));
		when(movementRepository.findMovementByGameAndXAndY(gameReturn, 0, 1)).thenReturn(Optional.empty());

		// @formatter:off
		List<MovementEntity> finishGame = 
			Arrays.asList(
				new MovementEntity(null, "X", 0, 0), 
				new MovementEntity(null, "X", 1, 0), 
				new MovementEntity(null, "X", 2, 0)
			);
		// @formatter:on

		when(movementRepository.findAllByGame(gameReturn)).thenReturn(finishGame);

		GameMovementResponseDto responseDto = gameMovementService.execute(id, requestDto);

		assertThat(responseDto.getStatus()).isEqualTo("Partida finalizada");
		assertThat(responseDto.getWinner()).isEqualTo("X");
	}

	@Test
	@Order(6)
	public void shouldToReturnFinishedDrawGame() throws GameMovementException {
		UUID id = UUID.randomUUID();

		var gameReturn = new GameEntity();
		gameReturn.setId(id);
		gameReturn.setStatus(GameStatus.INPROGRESS);
		gameReturn.setActualPlayer("X");

		var position = new GameMovementPositionDto();
		position.setX(0);
		position.setY(0);

		var requestDto = new GameMovementRequestDto();
		requestDto.setPlayer("X");
		requestDto.setPosition(position);

		when(gameRepository.findById(id)).thenReturn(Optional.of(gameReturn));
		when(movementRepository.findMovementByGameAndXAndY(gameReturn, 0, 0)).thenReturn(Optional.empty());

		// @formatter:off
		List<MovementEntity> drawBoard = 
			Arrays.asList(
				new MovementEntity(null, "X", 0, 0), 
				new MovementEntity(null, "X", 0, 1), 
				new MovementEntity(null, "O", 0, 2),
				
				new MovementEntity(null, "O", 1, 0), 
				new MovementEntity(null, "O", 1, 1), 
				new MovementEntity(null, "X", 1, 2),
				
				new MovementEntity(null, "X", 2, 0), 
				new MovementEntity(null, "X", 2, 1), 
				new MovementEntity(null, "O", 2, 2)
			);
		// @formatter:on

		when(movementRepository.findAllByGame(gameReturn)).thenReturn(drawBoard);

		GameMovementResponseDto responseDto = gameMovementService.execute(id, requestDto);

		assertThat(responseDto.getStatus()).isEqualTo("Partida finalizada");
		assertThat(responseDto.getWinner()).isEqualTo("Draw");
	}

	@Test
	@Order(7)
	public void shouldToReturnNullForNexMovement() throws GameMovementException {
		UUID id = UUID.randomUUID();

		var gameReturn = new GameEntity();
		gameReturn.setId(id);
		gameReturn.setStatus(GameStatus.INPROGRESS);
		gameReturn.setActualPlayer("X");

		var position = new GameMovementPositionDto();
		position.setX(2);
		position.setY(0);

		var requestDto = new GameMovementRequestDto();
		requestDto.setPlayer("X");
		requestDto.setPosition(position);

		when(gameRepository.findById(id)).thenReturn(Optional.of(gameReturn));
		when(movementRepository.findMovementByGameAndXAndY(gameReturn, 0, 1)).thenReturn(Optional.empty());

		// @formatter:off
		List<MovementEntity> inProgressGame = 
			Arrays.asList(
				new MovementEntity(null, "X", 0, 0), 
				new MovementEntity(null, "O", 1, 0)
			);
		// @formatter:on

		when(movementRepository.findAllByGame(gameReturn)).thenReturn(inProgressGame);

		GameMovementResponseDto responseDto = gameMovementService.execute(id, requestDto);

		assertThat(responseDto).isNull();
	}
}
