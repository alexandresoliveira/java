package dev.alexandreoliveira.apps.tictactoe.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStartResponseDto {

	private UUID id;
	private String firstPlayer;
}
