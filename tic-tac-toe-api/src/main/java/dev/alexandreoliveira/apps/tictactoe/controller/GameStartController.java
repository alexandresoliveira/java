package dev.alexandreoliveira.apps.tictactoe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartService;

@RestController
@RequestMapping("v1/game")
public class GameStartController {

	private final GameTicTacToeStartService service;

	public GameStartController(GameTicTacToeStartService service) {
		this.service = service;
	}

//	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<GameStartResponseDto> handle(UriComponentsBuilder uriBuilder) {
//		GameStartResponseDto response = service.execute();
//		URI uri = uriBuilder.path("/game/{id}").buildAndExpand(response.getId()).toUri();
//		return ResponseEntity.created(uri).body(response);
//	}
}
