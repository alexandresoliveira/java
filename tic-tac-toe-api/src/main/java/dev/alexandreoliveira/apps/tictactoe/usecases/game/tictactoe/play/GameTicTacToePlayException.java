package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

public final class GameTicTacToePlayException extends RuntimeException {

  public GameTicTacToePlayException(final String message) {
    super(message);
  }

  public GameTicTacToePlayException(
    final String message,
    final Throwable throwable
  ) {
    super(
      message,
      throwable
    );
  }
}
