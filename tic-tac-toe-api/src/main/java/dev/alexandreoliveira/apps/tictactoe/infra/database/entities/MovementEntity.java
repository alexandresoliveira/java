package dev.alexandreoliveira.apps.tictactoe.infra.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "movements")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MovementEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id")
  private GameEntity game;

  @Column(name = "player")
  @Pattern(regexp = "X|O")
  private String player;

  @Column(name = "x")
  @NotNull
  @Max(value = 2)
  @Min(value = 0)
  private Integer x;

  @Column(name = "y")
  @NotNull
  @Max(value = 2)
  @Min(value = 0)
  private Integer y;

}
