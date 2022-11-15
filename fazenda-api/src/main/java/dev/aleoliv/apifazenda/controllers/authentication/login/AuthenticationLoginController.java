package dev.aleoliv.apifazenda.controllers.authentication.login;

import dev.aleoliv.apifazenda.services.ApiService;
import dev.aleoliv.apifazenda.services.authentication.login.AuthenticationLoginServiceInput;
import dev.aleoliv.apifazenda.services.authentication.login.AuthenticationLoginServiceOutput;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("v1/auth/login")
public class AuthenticationLoginController {
  private final ApiService<AuthenticationLoginServiceInput, AuthenticationLoginServiceOutput> service;

  public AuthenticationLoginController(
    @Qualifier("authenticationLoginService")
    ApiService<AuthenticationLoginServiceInput, AuthenticationLoginServiceOutput> service
  ) {
    this.service = service;
  }

  @ResponseStatus(code = HttpStatus.OK)
  @ResponseBody
  @PostMapping(
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public ResponseEntity<AuthenticationLoginControllerResponse> handle(
    @RequestBody
    @Valid
    AuthenticationLoginControllerRequest request
  ) {
    var input = new AuthenticationLoginServiceInput(
      UUID.randomUUID(),
      request.email(),
      request.password()
    );

    var output = service.execute(input);

    return ResponseEntity.ok(new AuthenticationLoginControllerResponse(
      output.token(),
      output.type()
    ));
  }
}
