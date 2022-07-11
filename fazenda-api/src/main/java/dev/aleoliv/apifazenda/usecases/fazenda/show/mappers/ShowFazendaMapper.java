package dev.aleoliv.apifazenda.usecases.fazenda.show.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.usecases.fazenda.show.dtos.ShowFazendaResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class ShowFazendaMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public ShowFazendaMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();
		this.mapperFactory.classMap(ShowFazendaResponseDTO.class, FazendaEntity.class).byDefault().register();
		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public List<ShowFazendaResponseDTO> convertListDtoWith(List<FazendaEntity> entities) {
		return this.mapperFacade.mapAsList(entities, ShowFazendaResponseDTO.class);
	}

	public ShowFazendaResponseDTO convertDtoWith(FazendaEntity entity) {
		return this.mapperFacade.map(entity, ShowFazendaResponseDTO.class);
	}
}
