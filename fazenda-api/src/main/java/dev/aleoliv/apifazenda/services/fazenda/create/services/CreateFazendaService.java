package dev.aleoliv.apifazenda.services.fazenda.create.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.FarmRepository;
import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.fazenda.create.dtos.CreateFazendaRequestDTO;
import dev.aleoliv.apifazenda.services.fazenda.create.dtos.CreateFazendaResponseDTO;
import dev.aleoliv.apifazenda.services.fazenda.create.mappers.CreateFazendaMapper;

@Service
public class CreateFazendaService {

	private static final Logger logger = LogManager.getLogger("fazendaLogger");
	
	private final FarmRepository repository;
	private final CreateFazendaMapper mapper;

	public CreateFazendaService(FarmRepository repository, CreateFazendaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;		
	}
	
	public CreateFazendaResponseDTO execute(CreateFazendaRequestDTO dto) throws ServiceException {
		Optional<FarmEntity> optionalFazenda = repository.findByNome(dto.getNome());
		if (optionalFazenda.isPresent()) {
			String msg = String.format("CreateFazendaService::execute - %s - Já existe fazenda com este nome, por favor escolha outro!", dto.getNome());
			logger.info(msg);
			throw new ServiceException("Já existe fazenda com este nome, por favor escolha outro",
        traceLoggerId);
		}
		FarmEntity entity = mapper.createFazendaEntityWith(dto);
		FarmEntity farmEntity = repository.save(entity);
		return mapper.createDtoWith(farmEntity);
	}
}
