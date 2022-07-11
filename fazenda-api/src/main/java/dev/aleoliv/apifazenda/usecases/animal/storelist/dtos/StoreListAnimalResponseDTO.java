package dev.aleoliv.apifazenda.usecases.animal.storelist.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoreListAnimalResponseDTO {

	private UUID id;
	private String tag;
	private String fazenda;
}
