package dev.alexandreoliveira.apps.tictactoe.infra.http.controllers.v1.game.tictactoe.start;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

record GameTicTacToeStartRequest(
  @NotNull
  @NotEmpty
  @Pattern(regexp = "[XO]")
  String startPlayer
) implements Serializable {
}
