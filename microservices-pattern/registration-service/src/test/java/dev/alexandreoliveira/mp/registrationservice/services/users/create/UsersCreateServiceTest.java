package dev.alexandreoliveira.mp.registrationservice.services.users.create;

import dev.alexandreoliveira.mp.registrationservice.database.jpa.entities.UserEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersCreateServiceTest {

  @Test
  @Order(1)
  void shouldExceptionWhenDataIsInvalid() {
    var input = new UsersCreateServiceInput(
      "",
      "",
      ""
    );

    UsersCreateServiceDAO dao = mock(UsersCreateServiceDAO.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    var service = new UsersCreateService(
      dao,
      factory.getValidator()
    );

    ConstraintViolationException exception = assertThrows(
      ConstraintViolationException.class,
      () -> service.execute(input)
    );

    assertEquals(
      6,
      exception.getConstraintViolations().size()
    );
  }

  @Test
  @Order(2)
  void mustReturnCorrectDataWhenInputIsValid() {
    var input = new UsersCreateServiceInput(
      "User Test",
      "user-test@email.com",
      "11122233"
    );

    var mockUserEntity = new UserEntity();
    mockUserEntity.setName(input.name());
    mockUserEntity.setEmail(input.email());
    mockUserEntity.setPassword(input.password());

    var userEntity = new UserEntity();
    userEntity.setId(UUID.randomUUID());
    userEntity.setName(input.name());
    userEntity.setEmail(input.email());

    UsersCreateServiceDAO dao = mock(UsersCreateServiceDAO.class);
    when(dao.createUser(mockUserEntity)).thenReturn(userEntity);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    var service = new UsersCreateService(
      dao,
      factory.getValidator()
    );

    var output = service.execute(input);

    assertNotNull(output.id());
  }
}
