package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels;

import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.BaseRestController;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.create.TransfersControllerCreateRequest;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.create.TransfersControllerCreateResponse;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.index.TransfersControllerIndexRequest;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.rest.hotels.index.TransfersControllerIndexResponse;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.services.TransfersService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("transfers")
class TransfersController extends BaseRestController {

    private final TransfersService service;

    public TransfersController(TransfersService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransfersControllerCreateResponse> create(
            @RequestBody @Valid TransfersControllerCreateRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        TransfersControllerCreateResponse response = service.create(request);
        URI uri = uriComponentsBuilder.path("transfers/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransfersControllerIndexResponse> index(
            @Valid TransfersControllerIndexRequest request) {
        return ResponseEntity.ok(service.index(request));
    }

}
