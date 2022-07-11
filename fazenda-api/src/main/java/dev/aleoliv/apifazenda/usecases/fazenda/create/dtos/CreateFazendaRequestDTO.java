package dev.aleoliv.apifazenda.usecases.fazenda.create.dtos;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateFazendaRequestDTO {

	@NotBlank
	@Size(min = 1, max = 80)
	private String nome;
}
