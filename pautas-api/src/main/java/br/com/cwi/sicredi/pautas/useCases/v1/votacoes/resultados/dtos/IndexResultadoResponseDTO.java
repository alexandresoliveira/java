package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IndexResultadoResponseDTO {

    private String pauta;
    private String status;
    private Date dataEncerramento;
    private BigInteger numVotosSim;
    private BigInteger numVotosNao;
}
