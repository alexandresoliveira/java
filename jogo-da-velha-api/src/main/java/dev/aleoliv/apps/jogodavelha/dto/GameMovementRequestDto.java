package dev.aleoliv.apps.jogodavelha.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GameMovementRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private UUID id;
	
	@NotNull
	@Pattern(regexp = "X|O")
	private String player;

	@NotNull
	@Valid
	private GameMovementPositionDto position;
}
