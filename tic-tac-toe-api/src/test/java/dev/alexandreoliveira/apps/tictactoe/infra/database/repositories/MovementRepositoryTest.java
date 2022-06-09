package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@EnableJpaAuditing
@TestPropertySource(locations = {"classpath:application.yml"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.MovementRepositoryTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional(propagation = NOT_SUPPORTED)
public class MovementRepositoryTest {

  @Autowired
  MovementRepository sut;

  @Autowired
  PlatformTransactionManager platformTransactionManager;

  @Test
  @Order(1)
  void should_all_objects_are_instantiated() {
    assertThat(sut).isNotNull();
    assertThat(platformTransactionManager).isNotNull();
  }

  @Test
  @Order(2)
  @Sql(
    scripts = {"classpath:test-repositories/test-movement.sql"}
  )
  void should_test() {
    UUID gameId = UUID.fromString("cd64aa77-c933-4f86-b90e-2e624395dc0f");
    LocalDateTime localDateTime = LocalDateTime.now().minusHours(1);

    List<MovementEntity> all = sut.findAllMovementsByGameIdAndCreatedDateAfterParam(
      gameId,
      localDateTime
    );

    assertThat(all).isNotEmpty();
    assertThat(all.size()).isEqualTo(1);
  }
}
