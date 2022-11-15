package dev.aleoliv.apifazenda.services.animal.create;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnimalCreateServiceOutput {

	private UUID id;
	private String tag;
	private UUID idFazenda;
	private String fazenda;
}
