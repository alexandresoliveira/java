package dev.alexandreoliveira.apps.tictactoe.infra.database.repositories;

import dev.alexandreoliveira.apps.tictactoe.database.entity.GameStatus;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataJpaTest
@EnableJpaAuditing
@TestPropertySource(locations = {"classpath:application.yml"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepositoryTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameRepositoryTest {

  @Autowired
  GameRepository gameRepository;

  @Test
  @Order(1)
  void should_expected_all_objects_are_instances() {
    Assertions.assertNotNull(gameRepository);
  }

  @Test
  @Order(2)
  void should_expected_one_game_saved() {
    var game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setStatus(GameStatus.INPROGRESS);

    gameRepository.save(game_1);

    List<GameEntity> all = gameRepository.findAll();
    Assertions.assertFalse(all.isEmpty());

    GameEntity gameEntity = all.get(0);
    Assertions.assertNotNull(gameEntity.getCreatedAt());
    Assertions.assertNotNull(gameEntity.getUpdatedAt());
  }

  @Test
  @Order(3)
  void should_expected_an_exception_when_game_data_is_invalid() {
    RuntimeException exception = Assertions.assertThrows(
      RuntimeException.class,
      () -> gameRepository.save(new GameEntity()),
      "Expected an exception"
    );

    Assertions.assertNotNull(exception);
  }
}
