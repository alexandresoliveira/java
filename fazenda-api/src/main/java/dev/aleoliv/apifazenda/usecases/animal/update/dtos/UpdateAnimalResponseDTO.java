package dev.aleoliv.apifazenda.usecases.animal.update.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateAnimalResponseDTO {

	private UUID id;
	private String tag;
	private String fazenda;
}
