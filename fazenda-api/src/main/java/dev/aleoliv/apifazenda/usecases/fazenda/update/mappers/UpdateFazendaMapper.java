package dev.aleoliv.apifazenda.usecases.fazenda.update.mappers;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.usecases.fazenda.update.dtos.UpdateFazendaRequestDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.update.dtos.UpdateFazendaResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class UpdateFazendaMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public UpdateFazendaMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory.classMap(UpdateFazendaRequestDTO.class, FazendaEntity.class).byDefault().register();
		this.mapperFactory.classMap(UpdateFazendaResponseDTO.class, FazendaEntity.class).byDefault().register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public FazendaEntity createFazendaEntityWith(UpdateFazendaRequestDTO dto) {
		return mapperFacade.map(dto, FazendaEntity.class);
	}

	public UpdateFazendaResponseDTO createDtoWith(FazendaEntity entity) {
		return mapperFacade.map(entity, UpdateFazendaResponseDTO.class);
	}
}
