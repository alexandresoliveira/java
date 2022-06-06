package dev.alexandreoliveira.apps.tictactoe.infra.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.BaseEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
