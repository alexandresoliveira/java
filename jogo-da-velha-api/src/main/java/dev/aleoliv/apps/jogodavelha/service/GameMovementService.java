package dev.aleoliv.apps.jogodavelha.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apps.jogodavelha.component.GameComponent;
import dev.aleoliv.apps.jogodavelha.database.entity.GameEntity;
import dev.aleoliv.apps.jogodavelha.database.entity.GameStatus;
import dev.aleoliv.apps.jogodavelha.database.entity.MovementEntity;
import dev.aleoliv.apps.jogodavelha.database.repository.GameRepository;
import dev.aleoliv.apps.jogodavelha.database.repository.MovementRepository;
import dev.aleoliv.apps.jogodavelha.dto.GameMovementRequestDto;
import dev.aleoliv.apps.jogodavelha.dto.GameMovementResponseDto;
import dev.aleoliv.apps.jogodavelha.service.exception.GameMovementException;

@Service
public class GameMovementService {

	private final GameRepository gameRepository;
	private final MovementRepository movementRepository;
	private final GameComponent gameComponent;

	public GameMovementService(GameRepository gameRepository, MovementRepository movementRepository,
			GameComponent gameComponent) {
		this.gameRepository = gameRepository;
		this.movementRepository = movementRepository;
		this.gameComponent = gameComponent;
	}

	// @formatter:off
	
	public GameMovementResponseDto execute(UUID id, GameMovementRequestDto requestDto) throws GameMovementException {
		GameEntity game = gameRepository
				.findById(id)
				.orElseThrow(() -> new GameMovementException("Partida não encontrada"));
		
		if (game.getStatus().equals(GameStatus.DONE)) {
			return new GameMovementResponseDto("Partida Finalizada", game.getWinner() == "-" ? "Draw" : game.getWinner().toString());
		}

		if (!game.getActualPlayer().equalsIgnoreCase(requestDto.getPlayer())) {
			throw new GameMovementException("Não é o turno do jogador");
		}
		
		Optional<MovementEntity> optionalMovement = movementRepository
			.findMovementByGameAndXAndY(game, requestDto.getPosition().getX(), requestDto.getPosition().getY());
		
		if (optionalMovement.isPresent()) {
			var movement = optionalMovement.get();
			var message = String.format("Movimento inválido, x=%d, y=%d, player=%s", movement.getX(), movement.getY(), movement.getPlayer());
			throw new GameMovementException(message);
		}
		
		var actualMovement = new MovementEntity(game, requestDto.getPlayer(), requestDto.getPosition().getX(), requestDto.getPosition().getY());
		
		movementRepository.saveAndFlush(actualMovement);
		
		List<MovementEntity> movements = movementRepository.findAllByGame(game);
		
		gameComponent.checkBoard(movements);
		
		if (gameComponent.getIsFinish() && gameComponent.getWinner() != null) {
			game.setWinner(gameComponent.getWinner());
			game.setStatus(GameStatus.DONE);
			gameRepository.save(game);
			return new GameMovementResponseDto("Partida finalizada", gameComponent.getWinner().toString());
		} else if (gameComponent.getIsFinish()) {
			game.setWinner("-");
			game.setStatus(GameStatus.DONE);
			gameRepository.save(game);
			return new GameMovementResponseDto("Partida finalizada", "Draw");
		} else {
			game.setStatus(GameStatus.INPROGRESS);
			game.setActualPlayer(requestDto.getPlayer().equalsIgnoreCase("X") ? "O" : "X");
			gameRepository.save(game);
		}
		
		return null;
	}
	 
	// @formatter:on

}
