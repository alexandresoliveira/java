package dev.aleoliv.apifazenda.shared.database.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;

public interface AnimalRepository extends JpaRepository<AnimalEntity, UUID> {

	Optional<AnimalEntity> findByTagAndFazenda(String tag, FazendaEntity fazenda);
}
