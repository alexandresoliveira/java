package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.MovementRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
@ActiveProfiles("test-with-containers")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameTicTacToeMovementDAOImplTest {

  @Autowired
  GameTicTacToeMovementDAOImpl gameTicTacToeMovementDAO;

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

  @ParameterizedTest
  @MethodSource("should_find_game_with_id_data")
  @Order(1)
  void should_find_game_with_id(GameEntity entity) {
    GameEntity gameEntity = gameRepository.save(entity);

    GameEntity gameById = gameTicTacToeMovementDAO.findGameById(gameEntity.getId());

    assertNotNull(gameById);
    assertNotNull(gameById.getId());
  }

  static Stream<Arguments> should_find_game_with_id_data() {
    var game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setWinner("-");

    var game_2 = new GameEntity();
    game_2.setActualPlayer("X");
    game_2.setWinner("-");

    return Stream.of(
      Arguments.of(game_1),
      Arguments.of(game_2)
    );
  }

  @TestFactory
  @Order(2)
  List<DynamicTest> should_find_movement_by_game_and_positions() {
    GameEntity game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setWinner("-");

    MovementEntity movement_1 = new MovementEntity();
    movement_1.setY(1);
    movement_1.setX(1);
    movement_1.setPlayer("X");

    GameEntity savedGameEntity_1 = gameRepository.save(game_1);
    movement_1.setGame(savedGameEntity_1);
    movementRepository.save(movement_1);

    return List.of(
      dynamicTest("exists movement", () -> assertTrue(gameTicTacToeMovementDAO.findMovementByGameAndXAndY(savedGameEntity_1, 1, 1))),
      dynamicTest("no exists movement", () -> assertFalse(gameTicTacToeMovementDAO.findMovementByGameAndXAndY(savedGameEntity_1, 2, 1)))
    );
  }

  @Test
  @Order(3)
  void should_save_movement() {
    GameEntity gameEntity = new GameEntity();
    gameEntity.setActualPlayer("X");
    gameEntity.setWinner("-");

    GameEntity savedGameEntity = gameRepository.save(gameEntity);

    MovementEntity movementEntity = new MovementEntity();
    movementEntity.setGame(savedGameEntity);
    movementEntity.setX(1);
    movementEntity.setY(1);
    movementEntity.setPlayer("X");

    gameTicTacToeMovementDAO.saveMovement(movementEntity);

    List<MovementEntity> movementEntityList = movementRepository.findAll();
    assertThat(movementEntityList).isNotEmpty();
    assertThat(movementEntityList.size()).isEqualTo(1);
  }

  @Test
  @Order(4)
  void should_save_game() {
    GameEntity gameEntity = new GameEntity();
    gameEntity.setActualPlayer("X");
    gameEntity.setWinner("-");

    gameTicTacToeMovementDAO.saveGame(gameEntity);

    List<GameEntity> gameEntityList = gameRepository.findAll();
    assertThat(gameEntityList).isNotEmpty();
    assertThat(gameEntityList.size()).isEqualTo(1);
  }
}
