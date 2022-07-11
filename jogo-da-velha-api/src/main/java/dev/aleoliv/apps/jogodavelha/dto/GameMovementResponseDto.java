package dev.aleoliv.apps.jogodavelha.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameMovementResponseDto {

	private String status;
	private String winner;
}
