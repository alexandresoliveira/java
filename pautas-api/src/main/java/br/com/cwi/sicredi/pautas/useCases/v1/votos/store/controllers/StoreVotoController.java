package br.com.cwi.sicredi.pautas.useCases.v1.votos.store.controllers;

import br.com.cwi.sicredi.pautas.shared.utils.ApiVersionHelper;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.dtos.StoreVotoRequestDTO;
import br.com.cwi.sicredi.pautas.useCases.v1.votos.store.services.StoreVotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiVersionHelper.V_1 + "/votos")
public class StoreVotoController {

    private final StoreVotoService service;

    public StoreVotoController(StoreVotoService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void handler(@RequestBody @Valid StoreVotoRequestDTO dto) {
        service.execute(dto);
    }
}
