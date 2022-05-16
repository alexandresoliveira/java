package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

import java.util.UUID;

public record GameStartOutputDto(
  UUID id,
  String firstPlayer
) {
}
