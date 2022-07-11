package br.com.cwi.sicredi.pautas.useCases.v1.votos.store.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.entities.Voto;
import br.com.cwi.sicredi.pautas.shared.databases.enums.VotoResposta;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotacaoRepository;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotoRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.dtos.StoreVotoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.mappers.StoreVotoMapper;
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
public class StoreVotoServiceTest {

    @MockBean
    private VotoRepository votoRepository;

    @MockBean
    private VotacaoRepository votacaoRepository;

    @MockBean
    private StoreVotoMapper mapper;

    @InjectMocks
    private StoreVotoService storeVotoService;

    @Test
    @Order(1)
    public void shouldCreateVotoWithVotacaoAberta() {
        UUID votacaoId = UUID.randomUUID();
        String cpf = "65108183020";
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        Date encerraEm = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        StoreVotoRequestDTO requestDTO = new StoreVotoRequestDTO(votacaoId, cpf, VotoResposta.SIM);

        Voto entityIn = new Voto();
        entityIn.setCpf(cpf);
        entityIn.setResposta(VotoResposta.SIM);

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Votacao votacaoOut = new Votacao();
        votacaoOut.setId(votacaoId);
        votacaoOut.setDataEncerramento(encerraEm);

        Mockito.when(votacaoRepository.findById(votacaoId)).thenReturn(Optional.of(votacaoOut));

        Mockito.when(votoRepository.findByVotacaoAndCpf(votacaoOut, requestDTO.getCpf())).thenReturn(Optional.empty());

        Voto votoIn = new Voto();
        votoIn.setCpf(cpf);
        votoIn.setResposta(VotoResposta.SIM);
        votoIn.setDataVoto(new Date());
        votoIn.setVotacao(votacaoOut);

        Voto votoOut = new Voto();
        votoOut.setId(UUID.randomUUID());

        Mockito.when(votoRepository.saveAndFlush(votoIn)).thenReturn(votoOut);

        storeVotoService.execute(requestDTO);
    }

    @Test
    @Order(2)
    public void shouldReturnExceptionWhenCpfIsEmpty() {
        UUID votacaoId = UUID.randomUUID();
        String cpf = "";

        StoreVotoRequestDTO requestDTO = new StoreVotoRequestDTO(votacaoId, cpf, VotoResposta.SIM);

        Voto entityIn = new Voto();
        entityIn.setCpf(cpf);
        entityIn.setResposta(VotoResposta.SIM);

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            storeVotoService.execute(requestDTO);
        });

        Assertions.assertEquals("O cpf informado não é valido!", exception.getMessage());
    }

    @Test
    @Order(3)
    public void shouldReturnExceptionWhenCpfIsInvalid() {
        UUID votacaoId = UUID.randomUUID();
        String cpf = "00011122233";

        StoreVotoRequestDTO requestDTO = new StoreVotoRequestDTO(votacaoId, cpf, VotoResposta.SIM);

        Voto entityIn = new Voto();
        entityIn.setCpf(cpf);
        entityIn.setResposta(VotoResposta.SIM);

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            storeVotoService.execute(requestDTO);
        });

        Assertions.assertEquals("O cpf informado não é valido!", exception.getMessage());
    }

    @Test
    @Order(4)
    public void shouldReturnExceptionWhenVotacaoNotFound() {
        UUID votacaoId = UUID.randomUUID();
        String cpf = "65108183020";

        StoreVotoRequestDTO requestDTO = new StoreVotoRequestDTO(votacaoId, cpf, VotoResposta.SIM);

        Voto entityIn = new Voto();
        entityIn.setCpf(cpf);
        entityIn.setResposta(VotoResposta.SIM);

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Mockito.when(votacaoRepository.findById(votacaoId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            storeVotoService.execute(requestDTO);
        });

        Assertions.assertEquals("A sessão de votação não foi encontrada!", exception.getMessage());
    }

    @Test
    @Order(5)
    public void shouldReturnExceptionWhenDataVotacaoBeforeDataVoto() {
        UUID votacaoId = UUID.randomUUID();
        String cpf = "65108183020";
        LocalDateTime localDateTime = LocalDateTime.now().minusSeconds(1);
        Date encerraEm = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        StoreVotoRequestDTO requestDTO = new StoreVotoRequestDTO(votacaoId, cpf, VotoResposta.SIM);

        Voto entityIn = new Voto();
        entityIn.setCpf(cpf);
        entityIn.setResposta(VotoResposta.SIM);
        entityIn.setDataVoto(new Date());

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Votacao votacaoOut = new Votacao();
        votacaoOut.setId(votacaoId);
        votacaoOut.setDataEncerramento(encerraEm);

        Mockito.when(votacaoRepository.findById(votacaoId)).thenReturn(Optional.of(votacaoOut));

        Mockito.when(votoRepository.findByVotacaoAndCpf(votacaoOut, requestDTO.getCpf())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            storeVotoService.execute(requestDTO);
        });

        Assertions.assertEquals("A sessão de votação foi encerrada!", exception.getMessage());
    }

    @Test
    @Order(6)
    public void shouldReturnExceptionWhenVotoRegistred() {
        UUID votacaoId = UUID.randomUUID();
        String cpf = "65108183020";
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        Date encerraEm = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        StoreVotoRequestDTO requestDTO = new StoreVotoRequestDTO(votacaoId, cpf, VotoResposta.SIM);

        Voto entityIn = new Voto();
        entityIn.setCpf(cpf);
        entityIn.setResposta(VotoResposta.SIM);
        entityIn.setDataVoto(new Date());

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Votacao votacaoOut = new Votacao();
        votacaoOut.setId(votacaoId);
        votacaoOut.setDataEncerramento(encerraEm);

        Mockito.when(votacaoRepository.findById(votacaoId)).thenReturn(Optional.of(votacaoOut));

        Voto votoOut = new Voto();
        votoOut.setId(UUID.randomUUID());

        Mockito.when(votoRepository.findByVotacaoAndCpf(votacaoOut, requestDTO.getCpf())).thenReturn(Optional.of(votoOut));

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            storeVotoService.execute(requestDTO);
        });

        Assertions.assertEquals("O seu voto já foi registrado nesta sessão de votação!", exception.getMessage());
    }
}
