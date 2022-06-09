package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public record GameTicTacToeMovementInputDto(
  @NotNull UUID gameId,
  @NotNull @Pattern(regexp = "[XO]") String player,
  @NotNull @Max(value = 2) @Min(value = 0) Integer positionX,
  @NotNull @Max(value = 2) @Min(value = 0) Integer positionY
) {

}
