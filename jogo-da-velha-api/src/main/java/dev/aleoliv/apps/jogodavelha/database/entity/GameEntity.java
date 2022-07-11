package dev.aleoliv.apps.jogodavelha.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

	@Column(name = "actual_player")
	@NotNull
	@Pattern(regexp = "X|O")
	private String actualPlayer;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private GameStatus status = GameStatus.INPROGRESS;

	@Column(name = "winner")
	@Pattern(regexp = "X|O|-")
	private String winner;
}
