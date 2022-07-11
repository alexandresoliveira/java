package dev.aleoliv.apifazenda.usecases.animal.create.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.shared.database.repositories.FazendaRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.animal.create.dtos.CreateAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.create.dtos.CreateAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.create.mappers.CreateAnimalMapper;

@Service
public class CreateAnimalService {

	private final AnimalRepository animalRepository;
	private final FazendaRepository fazendaRepository;
	private final CreateAnimalMapper mapper;

	public CreateAnimalService(AnimalRepository animalRepository, FazendaRepository fazendaRepository, CreateAnimalMapper mapper) {
		this.animalRepository = animalRepository;
		this.fazendaRepository = fazendaRepository;
		this.mapper = mapper;
	}

	public CreateAnimalResponseDTO execute(CreateAnimalRequestDTO dto) throws ServiceException {
		
		Optional<FazendaEntity> optionalFazenda = fazendaRepository.findById(dto.getIdFazenda());
		if (!optionalFazenda.isPresent()) {
			throw new ServiceException("A fazenda deste animal não existe mais.");
		}
		
		Optional<AnimalEntity> optionalAnimal = animalRepository.findByTagAndFazenda(dto.getTag(), optionalFazenda.get());
		if (optionalAnimal.isPresent()) {
			throw new ServiceException("Já existe um animal com a tag para está fazenda.");
		}
		
		AnimalEntity animalEntity = mapper.createEntityWith(dto);
		animalEntity.setFazenda(optionalFazenda.get());
		AnimalEntity entity = animalRepository.save(animalEntity);
		return mapper.createDtoWith(entity);
	}
}
