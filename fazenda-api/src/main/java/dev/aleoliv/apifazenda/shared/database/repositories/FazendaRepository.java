package dev.aleoliv.apifazenda.shared.database.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;

public interface FazendaRepository extends JpaRepository<FazendaEntity, UUID> {

	Optional<FazendaEntity> findByNome(String nome);
}
