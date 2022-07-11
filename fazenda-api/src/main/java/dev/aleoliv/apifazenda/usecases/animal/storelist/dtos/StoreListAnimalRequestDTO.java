package dev.aleoliv.apifazenda.usecases.animal.storelist.dtos;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoreListAnimalRequestDTO {

	@NotNull
	@Size(min = 1, max = 80)
	private String tag;
	
	@NotNull
	private UUID idFazenda;
}
