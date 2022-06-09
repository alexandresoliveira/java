package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.RollbackException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@EnableJpaAuditing
@TestPropertySource(locations = {"classpath:application.yml"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepositoryTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameRepositoryTest {

  @Autowired
  GameRepository sut;

  @Autowired
  PlatformTransactionManager platformTransactionManager;

  @AfterEach
  void afterEachTest() {
    sut.deleteAll();
  }

  @Test
  @Order(1)
  void should_expected_all_objects_are_instances() {
    Assertions.assertNotNull(sut);
  }

  @Test
  @Order(2)
  void should_expected_one_game_saved() {
    var game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setStatus(GameStatus.INPROGRESS);

    sut.save(game_1);

    List<GameEntity> all = sut.findAll();
    Assertions.assertFalse(all.isEmpty());

    GameEntity gameEntity = all.get(0);
    Assertions.assertNotNull(gameEntity.getCreatedAt());
    Assertions.assertNotNull(gameEntity.getUpdatedAt());
  }

  @Test
  @Order(3)
  @Transactional(propagation = NOT_SUPPORTED)
  void should_expected_an_exception_when_game_data_is_invalid() {
    TransactionSystemException exception = Assertions.assertThrows(
      TransactionSystemException.class,
      () -> new TransactionTemplate(platformTransactionManager).execute(status ->
        sut.save(new GameEntity())
      ),
      "Expected an exception"
    );

    assertThat(exception).isNotNull();
    assertThat(exception.getCause()).isNotNull();
    assertThat(exception.getCause()).isInstanceOf(RollbackException.class);
  }
}
