package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.create.UsersControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.create.UsersControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.show.UsersControllerShowResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services.UsersService;
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
@RequestMapping("users")
public record UsersController(UsersService service) {

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsersControllerCreateResponse> create(
            @RequestBody @Valid UsersControllerCreateRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        UsersControllerCreateResponse response = service.create(request);
        URI uri = uriComponentsBuilder.path("users/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsersControllerShowResponse> show(@PathVariable("id") UUID id) {
        UsersControllerShowResponse response = service.show(id);
        return ResponseEntity.ok(response);
    }
}
