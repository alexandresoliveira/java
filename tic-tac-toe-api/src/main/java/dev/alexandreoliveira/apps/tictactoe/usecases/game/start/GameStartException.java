package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

public class GameStartException extends RuntimeException {
  public GameStartException(RuntimeException runtimeException) {
    super(runtimeException);
  }
}
