package dev.aleoliv.apifazenda.usecases.animal.update.dtos;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateAnimalRequestDTO {

	@NotNull
	private UUID id;

	@NotNull
	@Size(min = 1, max = 80)
	private String tag;

	@NotNull
	private UUID idFazenda;
}
