package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels;

import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.BaseRestController;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.create.HotelsCreateControllerRequest;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.create.HotelsCreateControllerResponse;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.index.HotelsIntexControllerRequest;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.rest.hotels.index.HotelsIntexControllerResponse;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.services.HotelsService;
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
@RequestMapping("hotels")
class HotelsController extends BaseRestController {

    private final HotelsService service;

    public HotelsController(HotelsService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HotelsCreateControllerResponse> create(
            @RequestBody @Valid HotelsCreateControllerRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        HotelsCreateControllerResponse response = service.create(request);
        URI uri = uriComponentsBuilder.path("hotels/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HotelsIntexControllerResponse> index(HotelsIntexControllerRequest request) {
        HotelsIntexControllerResponse response = service.index(request);
        return ResponseEntity.ok(response);
    }

}
