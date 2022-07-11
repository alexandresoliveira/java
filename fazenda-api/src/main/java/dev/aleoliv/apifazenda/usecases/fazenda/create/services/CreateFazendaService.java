package dev.aleoliv.apifazenda.usecases.fazenda.create.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.FazendaRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.fazenda.create.dtos.CreateFazendaRequestDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.create.dtos.CreateFazendaResponseDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.create.mappers.CreateFazendaMapper;

@Service
public class CreateFazendaService {

	private static final Logger logger = LogManager.getLogger("fazendaLogger");
	
	private final FazendaRepository repository;
	private final CreateFazendaMapper mapper;

	public CreateFazendaService(FazendaRepository repository, CreateFazendaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;		
	}
	
	public CreateFazendaResponseDTO execute(CreateFazendaRequestDTO dto) throws ServiceException {
		Optional<FazendaEntity> optionalFazenda = repository.findByNome(dto.getNome());
		if (optionalFazenda.isPresent()) {
			String msg = String.format("CreateFazendaService::execute - %s - Já existe fazenda com este nome, por favor escolha outro!", dto.getNome());
			logger.info(msg);
			throw new ServiceException("Já existe fazenda com este nome, por favor escolha outro");
		}
		FazendaEntity entity = mapper.createFazendaEntityWith(dto);
		FazendaEntity fazendaEntity = repository.save(entity);
		return mapper.createDtoWith(fazendaEntity);
	}
}
