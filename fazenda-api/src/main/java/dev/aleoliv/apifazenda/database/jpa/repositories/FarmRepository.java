package dev.aleoliv.apifazenda.database.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;

public interface FarmRepository extends JpaRepository<FarmEntity, UUID> {

	Optional<FarmEntity> findByNome(String nome);
}
