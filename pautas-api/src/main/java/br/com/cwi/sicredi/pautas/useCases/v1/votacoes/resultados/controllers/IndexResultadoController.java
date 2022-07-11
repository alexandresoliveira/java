package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.controllers;

import br.com.cwi.sicredi.pautas.shared.utils.ApiVersionHelper;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.dtos.IndexResultadoResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.resultados.services.IndexResultadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = ApiVersionHelper.V_1 + "/votacoes/resultado")
public class IndexResultadoController {

    private final IndexResultadoService service;

    public IndexResultadoController(IndexResultadoService service) {
        this.service = service;
    }

    @GetMapping(
            value = "{idVotacao}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public IndexResultadoResponseDTO handle(@PathVariable("idVotacao") UUID idVotacao) {
        return service.execute(idVotacao);
    }
}
