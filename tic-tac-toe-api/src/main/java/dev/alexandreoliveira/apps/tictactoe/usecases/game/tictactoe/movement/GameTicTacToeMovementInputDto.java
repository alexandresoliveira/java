package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GameTicTacToeMovementInputDto {

  @NotNull
  private final UUID gameId;

  @NotNull
  @Pattern(regexp = "X|O")
  private final String player;

  @NotNull
  @Max(value = 2)
  @Min(value = 0)
  private final Integer positionX;

  @NotNull
  @Max(value = 2)
  @Min(value = 0)
  private final Integer positionY;
}
