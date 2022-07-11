package dev.aleoliv.apifazenda.usecases.animal.update.mappers;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.usecases.animal.update.dtos.UpdateAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.update.dtos.UpdateAnimalResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class UpdateAnimalMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public UpdateAnimalMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory
			.classMap(UpdateAnimalRequestDTO.class, AnimalEntity.class)
			.field("idFazenda", "fazenda.id")
			.byDefault()
			.register();
		
		this.mapperFactory
			.classMap(UpdateAnimalResponseDTO.class, AnimalEntity.class)
			.field("fazenda", "fazenda.nome")
			.byDefault()
			.register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public AnimalEntity createEntityWith(UpdateAnimalRequestDTO dto) {
		return mapperFacade.map(dto, AnimalEntity.class);
	}

	public UpdateAnimalResponseDTO createDtoWith(AnimalEntity entity) {
		return mapperFacade.map(entity, UpdateAnimalResponseDTO.class);
	}
}
