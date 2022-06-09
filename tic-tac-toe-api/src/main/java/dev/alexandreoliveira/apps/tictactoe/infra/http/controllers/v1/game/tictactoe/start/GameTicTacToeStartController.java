package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.start;

import dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.BaseController;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.GameTicTacToeStartOutputDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("v1/game/tic-tac-toe/start")
public class GameTicTacToeStartController extends BaseController {
  private final TicTacToeService<GameTicTacToeStartInputDto, GameTicTacToeStartOutputDto> service;

  public GameTicTacToeStartController(TicTacToeService<GameTicTacToeStartInputDto, GameTicTacToeStartOutputDto> service) {
    this.service = service;
  }

  @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GameTicTacToeStartResponse> handle(
    @RequestBody
    @Valid GameTicTacToeStartRequest request,
    UriComponentsBuilder uriBuilder
  ) {
    var input = new GameTicTacToeStartInputDto();
    input.setStartPlayer(request.startPlayer());

    GameTicTacToeStartOutputDto output = service.execute(input);

    URI uri = uriBuilder.path("/game/tic-tac-toe/movement/{id}").buildAndExpand(output.id()).toUri();

    var response = new GameTicTacToeStartResponse(
      output.id(),
      output.firstPlayer()
    );

    return ResponseEntity.created(uri).body(response);
  }
}
