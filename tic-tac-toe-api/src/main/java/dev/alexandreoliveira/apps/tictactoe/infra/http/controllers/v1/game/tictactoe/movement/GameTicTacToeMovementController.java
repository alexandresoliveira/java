package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementOutputDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("v1/game/tic-tac-toe/{id}/movement")
public class GameTicTacToeMovementController {
  private final TicTacToeService<GameTicTacToeMovementInputDto, GameTicTacToeMovementOutputDto> service;

  public GameTicTacToeMovementController(
    @Qualifier("gameTicTacToeMovementService")
    TicTacToeService<GameTicTacToeMovementInputDto, GameTicTacToeMovementOutputDto> service
  ) {
    this.service = service;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<GameTicTacToeMovementControllerResponse> handle(
    @PathVariable("id") UUID id,
    @RequestBody @Valid GameTicTacToeMovementControllerRequest request,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    var input = new GameTicTacToeMovementInputDto(
      id,
      request.player(),
      request.positionX(),
      request.positionY()
    );

    var output = service.execute(input);

    var response = new GameTicTacToeMovementControllerResponse(
      output.getMessage(),
      output.getStatus(),
      output.getWinner()
    );

    URI uri = uriComponentsBuilder.path("v1/game/tic-tac-toe/{id}/status").buildAndExpand(id).toUri();
    return ResponseEntity.created(uri).body(response);
  }

}
