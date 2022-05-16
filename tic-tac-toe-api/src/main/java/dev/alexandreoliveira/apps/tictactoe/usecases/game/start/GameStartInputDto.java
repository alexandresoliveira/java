package dev.alexandreoliveira.apps.tictactoe.usecases.game.start;

import dev.alexandreoliveira.apps.tictactoe.shared.validation.groups.FieldValidationGroup;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@GroupSequence({FieldValidationGroup.class, GameStartInputDto.class})
public class GameStartInputDto implements Serializable {

  @Pattern(regexp = "X|O", groups = {FieldValidationGroup.class})
  private String startPlayer;
}
