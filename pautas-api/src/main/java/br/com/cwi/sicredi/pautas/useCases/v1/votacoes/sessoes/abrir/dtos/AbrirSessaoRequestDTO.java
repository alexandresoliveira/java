package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class AbrirSessaoRequestDTO {

    @NotNull
    private UUID pautaId;

    private Date encerraEm;
}
