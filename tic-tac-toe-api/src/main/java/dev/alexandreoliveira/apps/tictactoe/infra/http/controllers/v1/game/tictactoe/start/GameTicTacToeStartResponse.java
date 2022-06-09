package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.start;

import java.util.UUID;

record GameTicTacToeStartResponse(
  UUID id,
  String firstPlayer
) {
}
