package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementInputDto;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.GameTicTacToeMovementOutputDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
