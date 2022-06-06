package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

public class GameTicTacToeMovementException extends RuntimeException {

  public GameTicTacToeMovementException(final String message) {
    super(message);
  }

  public GameTicTacToeMovementException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
