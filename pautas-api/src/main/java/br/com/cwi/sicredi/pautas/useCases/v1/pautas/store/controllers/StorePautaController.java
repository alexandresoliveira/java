package br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.controllers;

import br.com.cwi.sicredi.pautas.shared.utils.ApiVersionHelper;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.dtos.StorePautaResponseDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.pautas.store.services.StorePautaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiVersionHelper.V_1 + "/pautas")
public class StorePautaController {

    private final StorePautaService service;

    public StorePautaController(StorePautaService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StorePautaResponseDTO handle(@RequestBody @Valid StorePautaRequestDTO dto) {
        return service.execute(dto);
    }
}
