package dev.aleoliv.apps.jogodavelha.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import dev.aleoliv.apps.jogodavelha.database.entity.GameEntity;
import dev.aleoliv.apps.jogodavelha.database.repository.GameRepository;
import dev.aleoliv.apps.jogodavelha.dto.GameStartResponseDto;

@Service
public class GameStartService {

	private final GameRepository gameRepository;

	public GameStartService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;

	}

	public GameStartResponseDto execute() {
		String firstPlayer = new Random().nextInt() % 2 == 0 ? "X" : "O";

		var gameEntity = new GameEntity();
		gameEntity.setActualPlayer(firstPlayer);
		gameEntity.setWinner("-");

		GameEntity savedEntity = gameRepository.save(gameEntity);

		return new GameStartResponseDto(savedEntity.getId(), firstPlayer);
	}
}
