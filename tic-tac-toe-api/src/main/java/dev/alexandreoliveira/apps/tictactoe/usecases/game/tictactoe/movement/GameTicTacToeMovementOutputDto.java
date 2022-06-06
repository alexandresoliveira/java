package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GameTicTacToeMovementOutputDto {
  private final String message;
  private final String status;
  private final String winner;
}
