package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GameTicTacToePlayOutputDto {
  private final Boolean isFinish;
  private final String winner;
}
