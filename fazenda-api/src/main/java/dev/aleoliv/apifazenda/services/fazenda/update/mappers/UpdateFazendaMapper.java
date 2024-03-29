package dev.aleoliv.apifazenda.services.fazenda.update.mappers;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.services.fazenda.update.dtos.UpdateFazendaRequestDTO;
import dev.aleoliv.apifazenda.services.fazenda.update.dtos.UpdateFazendaResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class UpdateFazendaMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public UpdateFazendaMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory.classMap(UpdateFazendaRequestDTO.class, FarmEntity.class).byDefault().register();
		this.mapperFactory.classMap(UpdateFazendaResponseDTO.class, FarmEntity.class).byDefault().register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public FarmEntity createFazendaEntityWith(UpdateFazendaRequestDTO dto) {
		return mapperFacade.map(dto, FarmEntity.class);
	}

	public UpdateFazendaResponseDTO createDtoWith(FarmEntity entity) {
		return mapperFacade.map(entity, UpdateFazendaResponseDTO.class);
	}
}
