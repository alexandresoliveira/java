package br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Pauta;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.PautaRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.mappers.StorePautaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StorePautaService {

    @Autowired
    private PautaRepository repository;

    @Autowired
    private StorePautaMapper mapper;

    public StorePautaResponseDTO execute(StorePautaRequestDTO dto) {

        Optional<Pauta> optionalPauta = repository.findByName(dto.getNome());
        if (optionalPauta.isPresent()) {
            throw new ServiceApiException("Já existe pauta com este mesmo nome!");
        }

        Pauta entity = mapper.createEntityWith(dto);
        Pauta pauta = repository.saveAndFlush(entity);
        return mapper.createResponseDtoWith(pauta);
    }
}
