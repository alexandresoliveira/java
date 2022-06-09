package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.start.impl;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.GameEntity;
import dev.alexandreoliveira.apps.tictactoe.infra.database.repositories.GameRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
@ActiveProfiles("test-with-postgressql-14.3")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameTicTacToeStartDAOImplTest {

  @Autowired
  GameTicTacToeStartDAOImpl sut;

  @Autowired
  GameRepository gameRepository;

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
    gameRepository.deleteAll();
  }

  @Test
  @Order(1)
  void must_be_save_an_new_game() {
    GameEntity game_1 = new GameEntity();
    game_1.setActualPlayer("X");
    game_1.setWinner("-");

    GameEntity savedGame_1 = sut.save(game_1);

    assertThat(savedGame_1).isNotNull();
    assertThat(savedGame_1.getId()).isNotNull();
    assertThat(savedGame_1.getCreatedAt()).isNotNull();
  }
}
