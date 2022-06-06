package dev.alexandreoliveira.apps.tictactoe.infra.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class GameEntity extends BaseEntity {

  @Column(name = "actual_player", nullable = false, length = 1)
  @NotNull
  @Pattern(regexp = "([XO])")
  private String actualPlayer;

  @Column(name = "status", nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GameStatus status = GameStatus.INPROGRESS;

  @Column(name = "winner", nullable = false, length = 1)
  @Pattern(regexp = "([XO\\-])")
  private String winner;
}
