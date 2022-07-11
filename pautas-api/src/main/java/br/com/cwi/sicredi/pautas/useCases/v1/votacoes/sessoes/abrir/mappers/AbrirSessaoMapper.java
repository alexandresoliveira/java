package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.mappers;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class AbrirSessaoMapper {

    private final MapperFactory mapperFactory;
    private final MapperFacade mapperFacade;

    public AbrirSessaoMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();

        this.mapperFactory
                .classMap(AbrirSessaoRequestDTO.class, Votacao.class)
                .field("pautaId", "pauta.id")
                .field("encerraEm", "dataEncerramento")
                .byDefault()
                .register();

        this.mapperFactory
                .classMap(AbrirSessaoResponseDTO.class, Votacao.class)
                .field("idVotacao", "id")
                .field("pauta", "pauta.name")
                .field("encerraEm", "dataEncerramento")
                .byDefault()
                .register();

        this.mapperFacade = this.mapperFactory.getMapperFacade();
    }

    public Votacao createEntityWith(AbrirSessaoRequestDTO dto) {
        return mapperFacade.map(dto, Votacao.class);
    }

    public AbrirSessaoResponseDTO createResponseDtoWith(Votacao entity) {
        return mapperFacade.map(entity, AbrirSessaoResponseDTO.class);
    }
}
