package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.enums.VotoResposta;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotacaoRepository;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotoRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.dtos.IndexResultadoResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.mappers.IndexResultadoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class IndexResultadoService {

    private static String EM_ANDAMENTO = "Em andamento";
    private static String ENCERRADO = "Encerrado";

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private IndexResultadoMapper mapper;

    public IndexResultadoResponseDTO execute(UUID idVotacao) {
        Optional<Votacao> optionalVotacao = votacaoRepository.findById(idVotacao);

        if (!optionalVotacao.isPresent()) {
            throw new ServiceApiException("Sessão de votação não encontrada!");
        }

        List<Map<String, Object>> votosByVotacao = votoRepository.countVotosByVotacao(optionalVotacao.get().getId());

        IndexResultadoResponseDTO responseDTO = mapper.convertDtoWithEntity(optionalVotacao.get());

        votosByVotacao.forEach(voto -> {
            if (voto.get("RESPOSTA").equals(VotoResposta.SIM.name())) {
                responseDTO.setNumVotosSim((BigInteger) voto.get("NUM_VOTOS"));
            } else if (voto.get("RESPOSTA").equals(VotoResposta.NAO.name())) {
                responseDTO.setNumVotosNao((BigInteger) voto.get("NUM_VOTOS"));
            }
        });

        responseDTO.setStatus(new Date().before(responseDTO.getDataEncerramento()) ? EM_ANDAMENTO : ENCERRADO);

        return responseDTO;
    }
}
