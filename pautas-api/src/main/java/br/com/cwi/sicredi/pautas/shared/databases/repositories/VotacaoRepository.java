package br.com.cwi.sicredi.pautas.shared.databases.repositories;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotacaoRepository extends JpaRepository<Votacao, UUID> {
}
