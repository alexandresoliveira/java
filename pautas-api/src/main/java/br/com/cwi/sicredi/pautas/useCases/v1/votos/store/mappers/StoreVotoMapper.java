package br.com.cwi.sicredi.pautas.useCases.v1.votos.store.mappers;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Voto;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.dtos.StoreVotoRequestDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreVotoMapper {

    private final MapperFactory mapperFactory;
    private final MapperFacade mapperFacade;

    public StoreVotoMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();

        this.mapperFactory
                .classMap(StoreVotoRequestDTO.class, Voto.class)
                .byDefault()
                .register();

        this.mapperFacade = this.mapperFactory.getMapperFacade();
    }

    public Voto createEntityWith(StoreVotoRequestDTO dto) {
        return mapperFacade.map(dto, Voto.class);
    }
}
