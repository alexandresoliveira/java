package dev.alexandreoliveira.mp.registrationservice.services.users.create;

import dev.alexandreoliveira.mp.registrationservice.configurations.jpa.JpaConfiguation;
import dev.alexandreoliveira.mp.registrationservice.database.jpa.entities.UserEntity;
import dev.alexandreoliveira.mp.registrationservice.database.jpa.repositories.UserRepository;
import dev.alexandreoliveira.mp.registrationservice.services.H2BaseDAOImplTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(
  includeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = {JpaConfiguation.class}
  )
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UsersCreateServiceDAOImplTest extends H2BaseDAOImplTest {

  @Autowired
  UserRepository userRepository;

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    buildRegistry(
      registry,
      UsersCreateServiceDAOImpl.class,
      false
    );
  }

  @BeforeEach
  void beforeEach() {
    userRepository.deleteAll();
  }

  @Test
  @Order(1)
  void shouldReturnExceptionWhenDataIsInvalid() {
    var daoImpl = new UsersCreateServiceDAOImpl(userRepository);

    var userEntity = new UserEntity();

    Exception exception = assertThrows(
      Exception.class,
      () -> daoImpl.createUser(userEntity)
    );

    assertThat(exception.getCause().getCause()).isExactlyInstanceOf(ConstraintViolationException.class);
  }

  @Test
  @Order(2)
  void shouldReturnUsersWhenContainName() {
    List<UserEntity> userEntityList = insertFakeDataFor(
      UserEntity.class,
      new String[]{
        "name_NAME",
        "email_EMAIL",
        "password_PASSWORD"
      },
      3
    );

    final String searchEmail = "test-email@email.com";

    userEntityList.get(1).setEmail(searchEmail);

    userRepository.saveAll(userEntityList);

    var daoImpl = new UsersCreateServiceDAOImpl(userRepository);

    Optional<UserEntity> userEntityOptional = daoImpl.findByEmail(
      searchEmail
    );

    assertThat(userEntityOptional).isPresent();
  }
}
