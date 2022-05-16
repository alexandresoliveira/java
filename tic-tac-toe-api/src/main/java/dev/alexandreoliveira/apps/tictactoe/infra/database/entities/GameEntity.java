package dev.alexandreoliveira.apps.tictactoe.infra.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import dev.alexandreoliveira.apps.tictactoe.database.entity.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class GameEntity extends BaseEntity {

	@Column(name = "actual_player", nullable = false, length = 1)
	@NotNull
	@Pattern(regexp = "X|O")
	private String actualPlayer;

	@Column(name = "status", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private GameStatus status = GameStatus.INPROGRESS;

	@Column(name = "winner", nullable = false, length = 1)
	@Pattern(regexp = "X|O|-")
	private String winner;
}
