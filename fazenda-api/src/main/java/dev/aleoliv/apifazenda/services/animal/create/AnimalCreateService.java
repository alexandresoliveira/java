package dev.aleoliv.apifazenda.services.animal.create;

import dev.aleoliv.apifazenda.database.jpa.entities.AnimalEntity;
import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("animalCreateService")
public class AnimalCreateService implements ApiService<AnimalCreateServiceInput, AnimalCreateServiceOutput> {
  private static final Logger logger = LoggerFactory.getLogger(AnimalCreateService.class);

  private final AnimalCreateServiceDAO dao;

  public AnimalCreateService(
    @Qualifier("animalCreateServiceDAOImpl")
    AnimalCreateServiceDAO dao
  ) {
    this.dao = dao;
  }

  @Override
  public AnimalCreateServiceOutput execute(
    AnimalCreateServiceInput input
  ) throws ServiceException {

    Optional<FarmEntity> optionalFazenda = farmRepository.findById(dto.getIdFazenda());
    if (!optionalFazenda.isPresent()) {
      throw new ServiceException(
        "A fazenda deste animal não existe mais.",
        input.traceLoggerId()
      );
    }

    Optional<AnimalEntity> optionalAnimal = animalRepository.findByTagAndFazenda(
      dto.getTag(),
      optionalFazenda.get()
    );
    if (optionalAnimal.isPresent()) {
      throw new ServiceException(
        "Já existe um animal com a tag para está fazenda.",
        input.traceLoggerId()
      );
    }

    AnimalEntity animalEntity = mapper.createEntityWith(dto);
    animalEntity.setFazenda(optionalFazenda.get());
    AnimalEntity entity = animalRepository.save(animalEntity);
    return mapper.createDtoWith(entity);
  }
}
