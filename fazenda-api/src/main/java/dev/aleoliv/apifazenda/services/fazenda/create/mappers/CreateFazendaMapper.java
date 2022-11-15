package dev.aleoliv.apifazenda.services.fazenda.create.mappers;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.services.fazenda.create.dtos.CreateFazendaRequestDTO;
import dev.aleoliv.apifazenda.services.fazenda.create.dtos.CreateFazendaResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class CreateFazendaMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public CreateFazendaMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory.classMap(CreateFazendaRequestDTO.class, FarmEntity.class).byDefault().register();
		this.mapperFactory.classMap(CreateFazendaResponseDTO.class, FarmEntity.class).byDefault().register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public FarmEntity createFazendaEntityWith(CreateFazendaRequestDTO dto) {
		return mapperFacade.map(dto, FarmEntity.class);
	}

	public CreateFazendaResponseDTO createDtoWith(FarmEntity entity) {
		return mapperFacade.map(entity, CreateFazendaResponseDTO.class);
	}
}
