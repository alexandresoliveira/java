package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.services;

import br.com.cwi.sicredi.pautas.shared.databases.entities.Pauta;
import br.com.cwi.sicredi.pautas.shared.databases.entities.Votacao;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.PautaRepository;
import br.com.cwi.sicredi.pautas.shared.databases.repositories.VotacaoRepository;
import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.mappers.AbrirSessaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class AbrirSessaoService {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private AbrirSessaoMapper mapper;

    public AbrirSessaoResponseDTO execute(AbrirSessaoRequestDTO dto) {
        Optional<Pauta> optionalPauta = pautaRepository.findById(dto.getPautaId());
        if (optionalPauta.isPresent()) {
            Votacao votacao = new Votacao();
            votacao.setPauta(optionalPauta.get());
            votacao.setDataEncerramento(verificarDataEncerramento(dto.getEncerraEm()));
            Votacao entity = votacaoRepository.saveAndFlush(votacao);
            return mapper.createResponseDtoWith(entity);
        }
        throw new ServiceApiException("Pauta não encontrada!");
    }

    private Date verificarDataEncerramento(Date encerraEm) {
        if (null == encerraEm) {
            LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        return encerraEm;
    }
}
