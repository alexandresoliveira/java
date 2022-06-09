package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.movement;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

record GameTicTacToeMovementControllerRequest(
  @NotNull @Pattern(regexp = "[XO]") String player,
  @NotNull @Max(value = 2) @Min(value = 0) Integer positionX,
  @NotNull @Max(value = 2) @Min(value = 0) Integer positionY
) {
}
