package dev.aleoliv.apifazenda.services.animal.index.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.aleoliv.apifazenda.database.jpa.entities.AnimalEntity;
import dev.aleoliv.apifazenda.services.animal.index.dtos.ShowAnimalResponseDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class ShowAnimalMapper {

	private final MapperFactory mapperFactory;
	private final MapperFacade mapperFacade;
	
	public ShowAnimalMapper() {
		this.mapperFactory = new DefaultMapperFactory.Builder().build();
		
		this.mapperFactory
			.classMap(ShowAnimalResponseDTO.class, AnimalEntity.class)
			.field("idFazenda", "fazenda.id")
			.field("fazenda", "fazenda.nome")
			.byDefault()
			.register();
		
		this.mapperFacade = this.mapperFactory.getMapperFacade();
	}
	
	public List<ShowAnimalResponseDTO> convertToListDtoWith(List<AnimalEntity> entities) {
		return this.mapperFacade.mapAsList(entities, ShowAnimalResponseDTO.class);
	}
	
	public ShowAnimalResponseDTO convertToDtoWith(AnimalEntity entity) {
		return this.mapperFacade.map(entity, ShowAnimalResponseDTO.class);
	}
}
