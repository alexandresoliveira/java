package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class IndexResultadoRequestDTO {

    @NotNull
    private UUID idVotacao;
}
