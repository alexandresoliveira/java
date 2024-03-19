package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.BaseRestController;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.create.DestinationsControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.create.DestinationsControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.index.DestinationsControllerIndexRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.index.DestinationsControllerIndexResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.update.DestinationsControllerUpdateRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.update.DestinationsControllerUpdateResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services.DestinationsService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("destinations")
class DestinationsController extends BaseRestController {

    private final DestinationsService service;

    public DestinationsController(DestinationsService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DestinationsControllerCreateResponse> create(
            @RequestBody @Valid DestinationsControllerCreateRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        service.create(request);
        URI uri = uriComponentsBuilder
                .path("destinations/{id}")
                .buildAndExpand(UUID.randomUUID()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = {"{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DestinationsControllerUpdateResponse> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid DestinationsControllerUpdateRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        service.update(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DestinationsControllerIndexResponse> index(
            @Valid DestinationsControllerIndexRequest request
    ) {
        DestinationsControllerIndexResponse response = service.index(request);
        return ResponseEntity.ok(response);
    }

}
