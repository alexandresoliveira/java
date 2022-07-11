package br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.controllers;

import br.com.cwi.sicredi.pautas.shared.utils.ApiVersionHelper;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.dtos.AbrirSessaoResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votacoes.sessoes.abrir.services.AbrirSessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiVersionHelper.V_1 + "/votacoes/sessoes")
public class AbrirSessaoController {

    private final AbrirSessaoService service;

    public AbrirSessaoController(AbrirSessaoService service) {
        this.service = service;
    }

    @PostMapping(
            value = "abrir",
            consumes = { MediaType.APPLICATION_JSON_VALUE }, 
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AbrirSessaoResponseDTO handler(@RequestBody @Valid AbrirSessaoRequestDTO dto) {
        return service.execute(dto);
    }
}
