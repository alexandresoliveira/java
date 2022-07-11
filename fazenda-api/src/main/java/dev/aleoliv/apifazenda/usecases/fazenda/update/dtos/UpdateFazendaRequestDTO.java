package dev.aleoliv.apifazenda.usecases.fazenda.update.dtos;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateFazendaRequestDTO {

	@NotNull
	private UUID id;

	@NotNull
	@Size(min = 1, max = 80)
	private String nome;
}
