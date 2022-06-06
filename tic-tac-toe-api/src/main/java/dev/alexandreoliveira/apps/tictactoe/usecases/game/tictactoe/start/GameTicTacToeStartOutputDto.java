package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start;

import java.util.UUID;

public record GameTicTacToeStartOutputDto(
  UUID id,
  String firstPlayer
) {
}
