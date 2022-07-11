package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Pauta;
import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotacaoRepository;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotoRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.dtos.IndexResultadoResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.mappers.IndexResultadoMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndexResultadoServiceTest {

    @MockBean
    private VotacaoRepository votacaoRepository;

    @MockBean
    private VotoRepository votoRepository;

    @MockBean
    private IndexResultadoMapper mapper;

    @InjectMocks
    private IndexResultadoService indexResultadoService;

    @Test
    @Order(1)
    public void shouldShowResultsForVotacaoSession() {
        UUID idVotacao = UUID.randomUUID();
        String pautaNome = "pauta::1";

        Pauta pautaOut = new Pauta();
        pautaOut.setName(pautaNome);

        Votacao votacaoOut = new Votacao();
        votacaoOut.setId(idVotacao);
        votacaoOut.setPauta(pautaOut);

        Mockito.when(votacaoRepository.findById(idVotacao)).thenReturn(Optional.of(votacaoOut));

        Map<String, Object> resultadoSim = new HashMap<>(2);
        resultadoSim.put("RESPOSTA", "SIM");
        resultadoSim.put("NUM_VOTOS", BigInteger.valueOf(2l));

        Map<String, Object> resultadoNao = new HashMap<>(2);
        resultadoNao.put("RESPOSTA", "NAO");
        resultadoNao.put("NUM_VOTOS", BigInteger.valueOf(4l));

        Mockito.when(votoRepository.countVotosByVotacao(votacaoOut.getId())).thenReturn(Arrays.asList(resultadoSim, resultadoNao));

        IndexResultadoResponseDTO responseDTO = new IndexResultadoResponseDTO();
        responseDTO.setDataEncerramento(new Date());

        Mockito.when(mapper.convertDtoWithEntity(votacaoOut)).thenReturn(responseDTO);

        IndexResultadoResponseDTO result = indexResultadoService.execute(idVotacao);

        Assertions.assertEquals(result.getNumVotosSim(), BigInteger.valueOf(2l));
        Assertions.assertEquals(result.getNumVotosNao(), BigInteger.valueOf(4l));
    }

    @Test
    @Order(2)
    public void shouldExpectedExceptionWhenVotacaoSessionNotFound() {
        UUID idVotacao = UUID.randomUUID();

        Mockito.when(votacaoRepository.findById(idVotacao)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            indexResultadoService.execute(idVotacao);
        });

        Assertions.assertEquals("Sessão de votação não encontrada!", exception.getMessage());
    }
}
