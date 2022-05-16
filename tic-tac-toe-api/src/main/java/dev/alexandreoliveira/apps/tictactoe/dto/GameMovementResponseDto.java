package dev.alexandreoliveira.apps.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameMovementResponseDto {

	private String status;
	private String winner;
}
