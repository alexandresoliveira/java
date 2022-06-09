package dev.alexandreoliveira.apps.tictactoe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class GameMovementPositionDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Max(value = 2)
  @Min(value = 0)
  private Integer x;

  @NotNull
  @Max(value = 2)
  @Min(value = 0)
  private Integer y;
}
