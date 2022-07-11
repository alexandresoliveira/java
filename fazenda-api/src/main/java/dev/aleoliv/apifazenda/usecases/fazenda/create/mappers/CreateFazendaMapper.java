package dev.aleoliv.apifazenda.usecases.fazenda.create.mappers;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.usecases.fazenda.create.dtos.CreateFazendaRequestDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.create.dtos.CreateFazendaResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class CreateFazendaMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public CreateFazendaMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory.classMap(CreateFazendaRequestDTO.class, FazendaEntity.class).byDefault().register();
		this.mapperFactory.classMap(CreateFazendaResponseDTO.class, FazendaEntity.class).byDefault().register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public FazendaEntity createFazendaEntityWith(CreateFazendaRequestDTO dto) {
		return mapperFacade.map(dto, FazendaEntity.class);
	}

	public CreateFazendaResponseDTO createDtoWith(FazendaEntity entity) {
		return mapperFacade.map(entity, CreateFazendaResponseDTO.class);
	}
}
