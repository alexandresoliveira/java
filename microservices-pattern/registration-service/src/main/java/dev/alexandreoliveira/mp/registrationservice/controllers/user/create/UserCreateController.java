package dev.alexandreoliveira.mp.registrationservice.controllers.user.create;

import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import dev.alexandreoliveira.mp.registrationservice.services.users.create.UsersCreateServiceInput;
import dev.alexandreoliveira.mp.registrationservice.services.users.create.UsersCreateServiceOutput;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("users/create")
class UserCreateController {

  private final AppService<UsersCreateServiceInput, UsersCreateServiceOutput> service;

  public UserCreateController(
    @Qualifier("UsersCreateService")
    AppService<UsersCreateServiceInput, UsersCreateServiceOutput> service
  ) {
    this.service = service;
  }

  @ResponseStatus(code = HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<UserCreateControllerResponse> handle(
    @RequestBody
    @Valid
    UserCreateControllerRequest request,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    var input = new UsersCreateServiceInput(
      request.name(),
      request.email(),
      request.password()
    );

    UsersCreateServiceOutput output = service.execute(input);

    URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(output.id()).toUri();

    return ResponseEntity.created(uri).body(new UserCreateControllerResponse(output));
  }
}
