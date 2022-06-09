package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start;

import dev.alexandreoliveira.apps.tictactoe.shared.validation.groups.FieldValidationGroup;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@GroupSequence({
  FieldValidationGroup.class,
  GameTicTacToeStartInputDto.class
})
public class GameTicTacToeStartInputDto implements Serializable {

  @Pattern(regexp = "X|O", groups = {FieldValidationGroup.class})
  private String startPlayer;
}
