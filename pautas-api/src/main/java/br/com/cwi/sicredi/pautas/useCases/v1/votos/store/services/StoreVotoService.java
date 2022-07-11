package br.com.cwi.sicredi.pautas.useCases.v1.votos.store.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.entities.Voto;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotacaoRepository;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotoRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.shared.utils.CpfHelper;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.dtos.StoreVotoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.mappers.StoreVotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class StoreVotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private StoreVotoMapper mapper;

    public void execute(StoreVotoRequestDTO dto) {
        Voto entity = mapper.createEntityWith(dto);
        entity.setDataVoto(new Date());

        if (!cpfValido(dto.getCpf())) {
            throw new ServiceApiException("O cpf informado não é valido!");
        }

        Optional<Votacao> optionalVotacao = votacaoRepository.findById(dto.getVotacaoId());
        if (!optionalVotacao.isPresent()) {
            throw new ServiceApiException("A sessão de votação não foi encontrada!");
        }

        Votacao votacao = optionalVotacao.get();
        if (!entity.getDataVoto().before(votacao.getDataEncerramento())) {
            throw new ServiceApiException("A sessão de votação foi encerrada!");
        }

        Optional<Voto> optionalVoto = votoRepository.findByVotacaoAndCpf(votacao, dto.getCpf());
        if (optionalVoto.isPresent()) {
            throw new ServiceApiException("O seu voto já foi registrado nesta sessão de votação!");
        }

        entity.setVotacao(votacao);

        votoRepository.saveAndFlush(entity);
    }

    /**
     * Não foi encotrado o recurso externo online disponibilizado no teste.
     * GET https://user-info.herokuapp.com/users/{cpf}
     *
     * @param cpf
     * @return
     */
    private boolean cpfValido(String cpf) {
        if (cpf.isEmpty()) return false;
        return new CpfHelper().isValid(cpf);
    }
}
