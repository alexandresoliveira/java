package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

public record GameTicTacToeMovementControllerResponse(
  String message,
  String status,
  String winner
) {
}
