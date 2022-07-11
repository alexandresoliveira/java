package dev.aleoliv.apps.jogodavelha.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apps.jogodavelha.database.entity.GameEntity;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
}
