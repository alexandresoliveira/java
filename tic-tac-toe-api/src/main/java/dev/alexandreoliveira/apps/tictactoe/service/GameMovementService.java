package dev.alexandreoliveira.apps.tictactoe.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.alexandreoliveira.apps.tictactoe.component.GameComponent;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.database.entity.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.database.entity.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.database.repository.MovementRepository;
import dev.alexandreoliveira.apps.tictactoe.service.exception.GameMovementException;
import org.springframework.stereotype.Service;

import dev.alexandreoliveira.apps.tictactoe.dto.GameMovementRequestDto;
import dev.alexandreoliveira.apps.tictactoe.dto.GameMovementResponseDto;

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
