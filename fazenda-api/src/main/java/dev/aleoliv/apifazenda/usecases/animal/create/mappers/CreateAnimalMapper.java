package dev.aleoliv.apifazenda.usecases.animal.create.mappers;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.usecases.animal.create.dtos.CreateAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.create.dtos.CreateAnimalResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class CreateAnimalMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public CreateAnimalMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory
			.classMap(CreateAnimalRequestDTO.class, AnimalEntity.class)
			.field("idFazenda", "fazenda.id")
			.byDefault()
			.register();
		
		this.mapperFactory
			.classMap(CreateAnimalResponseDTO.class, AnimalEntity.class)
			.field("idFazenda", "fazenda.id")
			.field("fazenda", "fazenda.nome")
			.byDefault()
			.register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public AnimalEntity createEntityWith(CreateAnimalRequestDTO dto) {
		return mapperFacade.map(dto, AnimalEntity.class);
	}

	public CreateAnimalResponseDTO createDtoWith(AnimalEntity entity) {
		return mapperFacade.map(entity, CreateAnimalResponseDTO.class);
	}
}
