package br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.mappers;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Pauta;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class StorePautaMapper {

    private final MapperFactory mapperFactory;
    private final MapperFacade mapperFacade;

    public StorePautaMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();

        this.mapperFactory
                .classMap(StorePautaRequestDTO.class, Pauta.class)
                .field("nome", "name")
                .byDefault()
                .register();

        this.mapperFactory
                .classMap(StorePautaResponseDTO.class, Pauta.class)
                .field("nome", "name")
                .field("dataCriacao", "createdAt")
                .byDefault()
                .register();

        this.mapperFacade = this.mapperFactory.getMapperFacade();
    }

    public Pauta createEntityWith(StorePautaRequestDTO dto) {
        return mapperFacade.map(dto, Pauta.class);
    }

    public StorePautaResponseDTO createResponseDtoWith(Pauta entity) {
        return mapperFacade.map(entity, StorePautaResponseDTO.class);
    }
}
