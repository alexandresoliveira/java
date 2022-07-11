package br.com.cwi.sicredi.pautas.shared.databases.repositories;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VotoRepository extends JpaRepository<Voto, UUID> {

    Optional<Voto> findByVotacaoAndCpf(Votacao votacao, String cpf);

    @Query(
        value = "select v.resposta, count(v.resposta) as num_votos from votos v where v.votacao_id = :votacaoId group by v.resposta",
        nativeQuery = true
    )
    List<Map<String, Object>> countVotosByVotacao(@Param("votacaoId") UUID votacaoId);
}
