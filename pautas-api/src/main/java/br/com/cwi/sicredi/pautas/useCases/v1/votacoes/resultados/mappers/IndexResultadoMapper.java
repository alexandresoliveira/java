package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.mappers;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.dtos.IndexResultadoResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class IndexResultadoMapper {

    private final MapperFactory mapperFactory;
    private final MapperFacade mapperFacade;

    public IndexResultadoMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();

        this.mapperFactory
                .classMap(IndexResultadoResponseDTO.class, Votacao.class)
                .field("pauta", "pauta.name")
                .field("dataEncerramento", "dataEncerramento")
                .byDefault()
                .register();

        this.mapperFacade = this.mapperFactory.getMapperFacade();
    }

    public IndexResultadoResponseDTO convertDtoWithEntity(Votacao votacao) {
        return mapperFacade.map(votacao, IndexResultadoResponseDTO.class);
    }
}
