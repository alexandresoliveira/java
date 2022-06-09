package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.MovementRepository;
import dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.impl.GameTicTacToeMovementDAOImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
@ActiveProfiles("test-with-postgressql-14.3")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameTicTacToePlayDAOImplTest {

  @Autowired
  GameTicTacToePlayDAOImpl sut;

  @Autowired
  GameRepository gameRepository;

  @Autowired
  MovementRepository movementRepository;

  @Container
  static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:14.3-alpine"))
    .withDatabaseName("tic-tac-toe-test-db")
    .withUsername("test")
    .withPassword("test");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    registry.add("spring.jpa.show-sql", () -> "true");
    registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQL10Dialect");
    registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    registry.add("spring.flyway.url", postgresqlContainer::getJdbcUrl);
    registry.add("spring.flyway.user", postgresqlContainer::getUsername);
    registry.add("spring.flyway.password", postgresqlContainer::getPassword);
    registry.add("spring.flyway.encoding", () -> "UTF-8");
  }

  @AfterEach
  void afterEachTest() {
    movementRepository.deleteAll();
    gameRepository.deleteAll();
  }

  @Test
  @Order(1)
  void should_expected_a_list_of_movements() {
    GameEntity game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setWinner("-");

    GameEntity savedGame_1 = gameRepository.save(game_1);

    MovementEntity movement_1 = new MovementEntity();
    movement_1.setPlayer("O");
    movement_1.setY(1);
    movement_1.setX(1);
    movement_1.setGame(savedGame_1);

    MovementEntity movement_2 = new MovementEntity();
    movement_2.setPlayer("X");
    movement_2.setY(0);
    movement_2.setX(1);
    movement_2.setGame(savedGame_1);

    movementRepository.saveAll(List.of(movement_1, movement_2));

    List<MovementEntity> movementEntityList = sut.findMovementsByGameId(savedGame_1.getId());

    assertThat(movementEntityList).isNotEmpty();
    assertThat(movementEntityList.size()).isEqualTo(2);
  }

  @Test
  @Order(2)
  void must_be_empty_movement_list_with_invalid_game_id() {
    GameEntity game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setWinner("-");

    GameEntity savedGame_1 = gameRepository.save(game_1);

    MovementEntity movement_1 = new MovementEntity();
    movement_1.setPlayer("O");
    movement_1.setY(1);
    movement_1.setX(1);
    movement_1.setGame(savedGame_1);

    MovementEntity movement_2 = new MovementEntity();
    movement_2.setPlayer("X");
    movement_2.setY(0);
    movement_2.setX(1);
    movement_2.setGame(savedGame_1);

    movementRepository.saveAll(List.of(movement_1, movement_2));

    List<MovementEntity> movementEntityList = sut.findMovementsByGameId(UUID.randomUUID());

    assertThat(movementEntityList).isEmpty();
  }
}
