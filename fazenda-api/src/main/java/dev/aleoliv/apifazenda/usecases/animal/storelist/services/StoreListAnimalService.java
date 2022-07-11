package dev.aleoliv.apifazenda.usecases.animal.storelist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.usecases.animal.storelist.dtos.StoreListAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.storelist.dtos.StoreListAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.storelist.mappers.StoreListAnimalMapper;

@Service
public class StoreListAnimalService {

	
	private final AnimalRepository repository;
	private final StoreListAnimalMapper mapper;

	public StoreListAnimalService(AnimalRepository repository, StoreListAnimalMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<StoreListAnimalResponseDTO> execute(List<StoreListAnimalRequestDTO> dtos) {
		List<AnimalEntity> animalsEntities = mapper.createEntityWith(dtos);
		List<AnimalEntity> all = repository.saveAll(animalsEntities);
		return mapper.createDtoWith(all);
	}
}
