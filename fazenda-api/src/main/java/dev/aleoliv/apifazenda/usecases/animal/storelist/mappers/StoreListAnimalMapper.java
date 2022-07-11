package dev.aleoliv.apifazenda.usecases.animal.storelist.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.usecases.animal.storelist.dtos.StoreListAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.storelist.dtos.StoreListAnimalResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class StoreListAnimalMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;

	public StoreListAnimalMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();

		this.mapperFactory
			.classMap(StoreListAnimalRequestDTO.class, AnimalEntity.class)
			.field("idFazenda", "fazenda.id")
			.byDefault()
			.register();
		
		this.mapperFactory
			.classMap(StoreListAnimalResponseDTO.class, AnimalEntity.class)
			.field("fazenda", "fazenda.nome")
			.byDefault()
			.register();

		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}

	public List<AnimalEntity> createEntityWith(List<StoreListAnimalRequestDTO> dtos) {
		return mapperFacade.mapAsList(dtos, AnimalEntity.class);
	}

	public List<StoreListAnimalResponseDTO> createDtoWith(List<AnimalEntity> entities) {
		return mapperFacade.mapAsList(entities, StoreListAnimalResponseDTO.class);
	}
}
