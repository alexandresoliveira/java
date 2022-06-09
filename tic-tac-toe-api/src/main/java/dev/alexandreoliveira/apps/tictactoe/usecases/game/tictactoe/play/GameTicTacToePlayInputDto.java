package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GameTicTacToePlayInputDto {

  @NotNull
  private final UUID gameId;
}
