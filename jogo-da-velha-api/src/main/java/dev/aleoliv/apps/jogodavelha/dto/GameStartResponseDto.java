package dev.aleoliv.apps.jogodavelha.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStartResponseDto {

	private UUID id;
	private String firstPlayer;
}
