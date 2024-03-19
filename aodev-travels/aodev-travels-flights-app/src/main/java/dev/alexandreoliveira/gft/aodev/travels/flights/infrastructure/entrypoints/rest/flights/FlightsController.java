package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights;

import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.BaseRestController;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexRequest;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.FlightsService;
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
@RequestMapping("flights")
public class FlightsController extends BaseRestController {

    private final FlightsService service;

    public FlightsController(FlightsService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FlightsControllerCreateResponse> create(
            @RequestBody @Valid FlightsControllerCreateRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        FlightsControllerCreateResponse response = service.create(request);
        URI uri = uriComponentsBuilder.path("flights/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FlightsControllerIndexResponse> index(@Valid FlightsControllerIndexRequest request) {
        FlightsControllerIndexResponse response = service.index(request);
        return ResponseEntity.ok(response);
    }
}
