package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.BaseRestController;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.create.ReservationsCreateControllerRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.create.ReservationsCreateControllerResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.show.ReservationsControllerShowResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services.ReservationsService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("reservations")
class ReservationsController extends BaseRestController {

    private final ReservationsService service;

    public ReservationsController(ReservationsService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReservationsCreateControllerResponse> create(
            @RequestBody @Valid ReservationsCreateControllerRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        ReservationsCreateControllerResponse response = service.create(request);
        URI uri = uriComponentsBuilder.path("reservations/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReservationsControllerShowResponse> show(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.show(id));
    }
}
