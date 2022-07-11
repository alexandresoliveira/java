package br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StorePautaRequestDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String nome;
}
