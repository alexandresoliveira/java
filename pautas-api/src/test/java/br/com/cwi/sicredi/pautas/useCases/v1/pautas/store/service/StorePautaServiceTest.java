package br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.service;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Pauta;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.PautaRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.mappers.StorePautaMapper;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.services.StorePautaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StorePautaServiceTest {

    @MockBean
    private PautaRepository pautaRepository;

    @MockBean
    private StorePautaMapper mapper;

    @InjectMocks
    private StorePautaService storePautaService;

    @Test
    @Order(1)
    public void shouldCreatePauta() {
        String nomePauta = "pauta::1";

        StorePautaRequestDTO requestDTO = new StorePautaRequestDTO(nomePauta);

        Pauta entityIn = new Pauta();
        entityIn.setName(nomePauta);
        entityIn.setCreatedAt(new Date());

        Mockito.when(mapper.createEntityWith(requestDTO)).thenReturn(entityIn);

        Pauta entityOut = new Pauta();
        entityOut.setId(UUID.randomUUID());
        entityOut.setName(nomePauta);
        entityOut.setCreatedAt(new Date());

        Mockito.when(pautaRepository.saveAndFlush(entityIn)).thenReturn(entityOut);

        StorePautaResponseDTO responseDTO = new StorePautaResponseDTO(
                entityOut.getId(),
                nomePauta,
                entityOut.getCreatedAt()
        );

        Mockito.when(mapper.createResponseDtoWith(entityOut)).thenReturn(responseDTO);

        StorePautaResponseDTO result = storePautaService.execute(requestDTO);

        Assertions.assertNotNull(result.getId());
    }

    @Test
    @Order(2)
    public void shouldReturnExceptionWhenNomePautaExists() {
        String nomePauta = "pauta::1";

        StorePautaRequestDTO requestDTO = new StorePautaRequestDTO(nomePauta);

        Pauta entityOut = new Pauta();
        entityOut.setName(nomePauta);

        Mockito.when(pautaRepository.findByName(requestDTO.getNome())).thenReturn(Optional.of(entityOut));

        Exception exception = Assertions.assertThrows(ServiceApiException.class, () -> {
            StorePautaResponseDTO result = storePautaService.execute(requestDTO);
        });

        Assertions.assertEquals("Já existe pauta com este mesmo nome!", exception.getMessage());
    }
}
