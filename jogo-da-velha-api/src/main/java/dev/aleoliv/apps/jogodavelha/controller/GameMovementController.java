package dev.aleoliv.apps.jogodavelha.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apps.jogodavelha.dto.GameMovementRequestDto;
import dev.aleoliv.apps.jogodavelha.dto.GameMovementResponseDto;
import dev.aleoliv.apps.jogodavelha.service.GameMovementService;
import dev.aleoliv.apps.jogodavelha.service.exception.GameMovementException;

@RestController
@RequestMapping("v1/game/{id}/movement")
public class GameMovementController {

	private final GameMovementService service;

	public GameMovementController(GameMovementService service) {
		this.service = service;
	}

	// @formatter:off

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> handle(
			@PathVariable("id") UUID id, 
			@RequestBody @Valid GameMovementRequestDto requestDto) throws GameMovementException {
		GameMovementResponseDto responseDto = service.execute(id, requestDto);
		return ResponseEntity.ok(responseDto);
	}
	
	// @formatter:on
}
