package dev.aleoliv.apifazenda.services.animal.index.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShowAnimalResponseDTO {

	private UUID id;
	private String tag;
	private UUID idFazenda;
	private String fazenda;
}
