package dev.aleoliv.apps.jogodavelha.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.aleoliv.apps.jogodavelha.dto.GameStartResponseDto;
import dev.aleoliv.apps.jogodavelha.service.GameStartService;

@RestController
@RequestMapping("v1/game")
public class GameStartController {

	private final GameStartService service;

	public GameStartController(GameStartService service) {
		this.service = service;
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GameStartResponseDto> handle(UriComponentsBuilder uriBuilder) {
		GameStartResponseDto response = service.execute();
		URI uri = uriBuilder.path("/game/{id}").buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
}
