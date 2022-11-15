package dev.aleoliv.apifazenda.database.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apifazenda.database.jpa.entities.AnimalEntity;
import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;

public interface AnimalRepository extends JpaRepository<AnimalEntity, UUID> {

	Optional<AnimalEntity> findByTagAndFazenda(String tag, FarmEntity fazenda);
}
