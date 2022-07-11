package br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StorePautaResponseDTO {

    private UUID id;
    private String nome;
    private Date dataCriacao;
}
