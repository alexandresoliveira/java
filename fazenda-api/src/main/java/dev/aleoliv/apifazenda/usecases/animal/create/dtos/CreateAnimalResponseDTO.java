package dev.aleoliv.apifazenda.usecases.animal.create.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAnimalResponseDTO {

	private UUID id;
	private String tag;
	private UUID idFazenda;
	private String fazenda;
}
