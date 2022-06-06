package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start;

public class GameTicTacToeStartException extends RuntimeException {
  public GameTicTacToeStartException(RuntimeException runtimeException) {
    super(runtimeException);
  }
}
