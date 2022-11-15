package dev.aleoliv.apifazenda.services.animal.create;

import dev.aleoliv.apifazenda.database.jpa.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.database.jpa.repositories.FarmRepository;
import org.springframework.stereotype.Component;

@Component("animalCreateServiceDAOImpl")
public class AnimalCreateServiceDAOImpl implements AnimalCreateServiceDAO {
  private final AnimalRepository animalRepository;
  private final FarmRepository farmRepository;

  public AnimalCreateServiceDAOImpl(
    AnimalRepository animalRepository,
    FarmRepository farmRepository
  ) {
    this.animalRepository = animalRepository;
    this.farmRepository = farmRepository;
  }
}
