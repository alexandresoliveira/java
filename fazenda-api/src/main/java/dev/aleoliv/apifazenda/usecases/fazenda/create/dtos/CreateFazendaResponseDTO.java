package dev.aleoliv.apifazenda.usecases.fazenda.create.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateFazendaResponseDTO {

	public UUID id;
	public String nome;
}
