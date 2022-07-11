package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Pauta;
import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.PautaRepository;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotacaoRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.mappers.AbrirSessaoMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbrirSessaoServiceTest {

    @MockBean
    private VotacaoRepository votacaoRepository;

    @MockBean
    private PautaRepository pautaRepository;

    @MockBean
    private AbrirSessaoMapper mapper;

    @InjectMocks
    private AbrirSessaoService abrirSessaoService;

    @Test
    @Order(1)
    public void shouldCreateSessaoVotacaoWithPauta() {
        UUID pautaId = UUID.randomUUID();
        UUID votacaoId = UUID.randomUUID();
        String nomePauta = "pauta::1";
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        Date encerraEm = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        AbrirSessaoRequestDTO requestDTO = new AbrirSessaoRequestDTO(pautaId, encerraEm);

        Pauta entityPautaOut = new Pauta();
        entityPautaOut.setId(pautaId);
        entityPautaOut.setName(nomePauta);

        Mockito.when(pautaRepository.findById(requestDTO.getPautaId())).thenReturn(Optional.of(entityPautaOut));

        Votacao entityVotacaoIn = new Votacao();
        entityVotacaoIn.setPauta(entityPautaOut);
        entityVotacaoIn.setDataEncerramento(encerraEm);

        Votacao entityVotacaoOut = new Votacao();
        entityVotacaoOut.setId(UUID.randomUUID());
        entityVotacaoOut.setPauta(entityPautaOut);
        entityVotacaoOut.setDataEncerramento(encerraEm);

        Mockito.when(votacaoRepository.saveAndFlush(entityVotacaoIn)).thenReturn(entityVotacaoOut);

        Mockito.when(mapper.createResponseDtoWith(entityVotacaoOut)).thenReturn(new AbrirSessaoResponseDTO(votacaoId, nomePauta, encerraEm));

        AbrirSessaoResponseDTO result = abrirSessaoService.execute(requestDTO);

        Assertions.assertNotNull(result.getIdVotacao());
        Assertions.assertTrue(new Date().before(result.getEncerraEm()));
    }

    @Test
    @Order(2)
    public void shouldExpectExceptionWhenNotFoundPauta() {
        UUID pautaId = UUID.randomUUID();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        Date encerraEm = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        AbrirSessaoRequestDTO requestDTO = new AbrirSessaoRequestDTO(pautaId, encerraEm);

        Mockito.when(pautaRepository.findById(requestDTO.getPautaId())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            abrirSessaoService.execute(requestDTO);
        });

        Assertions.assertEquals("Pauta não encontrada!", exception.getMessage());
    }
}
